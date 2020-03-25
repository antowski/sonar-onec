package org.antowski.sonar.plugins.onec;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.sonar.sslr.api.RecognitionException;
import com.sonar.sslr.api.typed.ActionParser;
import java.io.File;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.antowski.onec.parser.OneCParser;
import org.antowski.plugins.onec.api.tree.Tree;
import org.antowski.plugins.onec.api.visitors.TreeVisitor;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonarsource.analyzer.commons.ProgressReport;

public class OneCSensor implements Sensor {

    private SensorContext context;

    private final FileSystem fileSystem;
    private final FilePredicate mainFilePredicate;
    private final FileLinesContextFactory fileLinesContextFactory;

    private RuleKey parsingErrorRuleKey = null;

    private static final Logger LOG = Loggers.get(OneCSensor.class);

    public OneCSensor(FileSystem fileSystem, FileLinesContextFactory fileLinesContextFactory, CheckFactory checkFactory) {
        this.fileSystem = fileSystem;
        this.mainFilePredicate = fileSystem.predicates().and(
                fileSystem.predicates().hasType(InputFile.Type.MAIN),
                fileSystem.predicates().hasLanguage(OneC.KEY));
        this.fileLinesContextFactory = fileLinesContextFactory;        
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor
                .onlyOnLanguage(OneC.KEY)
                .name("1C 7.7 sensor")
                .onlyOnFileType(InputFile.Type.MAIN);
    }

    @Override
    public void execute(SensorContext context) {

        List<TreeVisitor> treeVisitors = Lists.newArrayList();

        ProgressReport progressReport = new ProgressReport(
                "Report about progress of 1C:Enterprise 7.7 analyzer",
                TimeUnit.SECONDS.toMillis(10));

        List<InputFile> inputFiles = new ArrayList<>();
        fileSystem.inputFiles(mainFilePredicate).forEach(inputFiles::add);

        progressReport.start(inputFiles.stream().map(InputFile::toString).collect(Collectors.toList()));

        analyseFiles(context, treeVisitors, inputFiles, progressReport);

        //MetricsVisitor metricsVisitor = new MetricsVisitor(context,fileLinesContextFactory);
        //treeVisitors.add(metricsVisitor);

    }

    @VisibleForTesting
    protected void analyseFiles(
            SensorContext context,
            List<TreeVisitor> treeVisitors,
            Iterable<InputFile> inputFiles,
            ProgressReport progressReport) {
        boolean success = false;
        try {
            for (InputFile inputFile : inputFiles) {
                if (context.isCancelled()) {
                    throw new CancellationException("Analysis interrupted because the SensorContext is in cancelled state");
                }
                analyseFile(context, inputFile, treeVisitors);
                progressReport.nextFile();
            }
            success = true;
        } catch (CancellationException e) {  //NOSONAR
            // do not propagate the exception
            LOG.debug(e.toString());
        } finally {
            stopProgressReport(progressReport, success);
        }
    }

    private void analyseFile(SensorContext sensorContext, InputFile inputFile, List<TreeVisitor> treeVisitors) {

        try {
            ActionParser<Tree> parser = OneCParser.createParser(sensorContext.fileSystem().encoding());
            Tree ast = parser.parse(new File(inputFile.absolutePath()));
            scanFile(inputFile, ast, treeVisitors);

        } catch (RecognitionException e) {
            checkInterrupted(e);
            LOG.error("Unable to parse file: " + inputFile.absolutePath());
            LOG.error(e.getMessage());
            processRecognitionException(e, sensorContext, inputFile);

        } catch (Exception e) {
            checkInterrupted(e);
            throw new AnalysisException("Unable to analyse file: " + inputFile.absolutePath(), e);
        }

    }

    private void scanFile(InputFile inputFile, Tree ast, List<TreeVisitor> visitors){

        LOG.info("analyse file '" + inputFile.absolutePath() + "'\n"
                + "\t language: " + inputFile.language() + "\n"
                + "\t charset: " + inputFile.charset());

        for (TreeVisitor visitor : visitors) {
            //visitor.scanTree(context);
        }

    }

    private static void stopProgressReport(ProgressReport progressReport, boolean success) {
        if (success) {
            progressReport.stop();
        } else {
            progressReport.cancel();
        }
    }

    private static void checkInterrupted(Exception e) {
        Throwable cause = Throwables.getRootCause(e);
        if (cause instanceof InterruptedException || cause instanceof InterruptedIOException) {
            throw new AnalysisException("Analysis cancelled", e);
        }
    }

    private void processRecognitionException(RecognitionException e, SensorContext sensorContext, InputFile inputFile) {
        if (parsingErrorRuleKey != null) {
            NewIssue newIssue = sensorContext.newIssue();

            NewIssueLocation primaryLocation = newIssue.newLocation()
                    .message(e.getMessage())
                    .on(inputFile)
                    .at(inputFile.selectLine(e.getLine()));

            newIssue
                    .forRule(parsingErrorRuleKey)
                    .at(primaryLocation)
                    .save();
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
