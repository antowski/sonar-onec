
package org.antowski.sonar.plugins.onec;

import com.google.common.collect.ImmutableList;
import org.antowski.onec.checks.CheckList;
import org.antowski.onec.FileLinesVisitor;
import org.antowski.onec.OneCAstScanner;
import org.antowski.onec.OneCConfiguration;
import org.antowski.onec.OneCMetric;

import com.google.common.collect.Lists;

import com.sonar.sslr.api.Grammar;

import java.io.File;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;

import org.sonar.api.utils.log.Loggers;
import org.sonar.api.utils.log.Logger;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.InputFile;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.measures.Metric;
import org.sonar.api.rule.RuleKey;

import org.sonar.squidbridge.AstScanner;
import org.sonar.squidbridge.SquidAstVisitor;
import org.sonar.squidbridge.api.CheckMessage;
import org.sonar.squidbridge.api.SourceCode;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.indexer.QueryByType;

import org.sonar.squidbridge.ProgressReport;

public class OneCSensor implements Sensor {

    private final Checks<SquidAstVisitor<Grammar>> checks;
    private final FileLinesContextFactory fileLinesContextFactory;

    private SensorContext context;
    private AstScanner<Grammar> scanner;

    private FileSystem fileSystem;

    private static final Logger LOG = Loggers.get(OneCSensor.class);

    public OneCSensor(FileLinesContextFactory fileLinesContextFactory, CheckFactory checkFactory) {
        this.checks = checkFactory
            .<SquidAstVisitor<Grammar>>create(CheckList.REPOSITORY_KEY)
            .addAnnotatedChecks(CheckList.getChecks());
        this.fileLinesContextFactory = fileLinesContextFactory;        
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor
                .onlyOnLanguage(OneC.KEY)
                .name("OneC sensor")
                .onlyOnFileType(InputFile.Type.MAIN);;
    }

    @Override
    public void execute(SensorContext context) {
        this.fileSystem = context.fileSystem();

        /*
        List<TreeVisitor> treeVisitors = Lists.newArrayList();

        FilePredicate mainFilePredicate = this.fileSystem.predicates().and(
                this.fileSystem.predicates().hasType(InputFile.Type.MAIN),
                this.fileSystem.predicates().hasLanguage(OneC.KEY));

        visitors = ImmutableList.<OneCCheck>builder().addAll(checks.all());

        MetricsVisitor metricsVisitor = new MetricsVisitor(
                context,
                noSonarFilter,
                context.settings().getBoolean(JavaScriptPlugin.IGNORE_HEADER_COMMENTS),
                fileLinesContextFactory,
                isAtLeastSq62);

        treeVisitors.add(metricsVisitor);
        treeVisitors.add(new HighlighterVisitor(context, fileSystem));
        treeVisitors.add(new SeChecksDispatcher(checks.seChecks()));
        treeVisitors.add(new CpdVisitor(fileSystem, context));
        treeVisitors.addAll(checks.visitorChecks());
*/
        /*
        ImmutableList<OneCCheck> visitors = getVisitors(new CpdVisitor(context));

        PHPAnalyzer phpAnalyzer = new PHPAnalyzer(fileSystem.encoding(), visitors);
        ArrayList<InputFile> inputFiles = Lists.newArrayList(fileSystem.inputFiles(mainFilePredicate));

        ProgressReport progressReport = new ProgressReport("Report about progress of PHP analyzer", TimeUnit.SECONDS.toMillis(10));
        progressReport.start(Lists.newArrayList(fileSystem.files(mainFilePredicate)));

        Map<File, Integer> numberOLinesOfCode = new HashMap<>();

        analyseFiles(context, phpAnalyzer, inputFiles, progressReport, numberOLinesOfCode);
*/
        //processCoverage(context, numberOLinesOfCode);
    }

    //@Override
    public void execute_old(SensorContext context) {

        /*
        LOG.info(" ------- OneCSensor works!");

        FileSystem fileSystem = context.fileSystem();

        FilePredicate OneCFilePredicate = fileSystem.predicates().and(
            fileSystem.predicates().hasLanguage(OneC.KEY));
        
        ArrayList<InputFile> inputFiles = Lists.newArrayList(fileSystem.inputFiles(OneCFilePredicate));

        ProgressReport progressReport = new ProgressReport("Report about progress of 1C:7.7 analyzer", TimeUnit.SECONDS.toMillis(10));
        progressReport.start(Lists.newArrayList(fileSystem.files(OneCFilePredicate)));

        analyseFiles(context, inputFiles, progressReport);

        */

        this.context = context;
        Map<InputFile, Set<Integer>> linesOfCode = new HashMap<>();

        OneCConfiguration conf = createConfiguration();

        List<SquidAstVisitor<Grammar>> visitors = Lists.newArrayList(checks.all());
        visitors.add(new FileLinesVisitor(fileLinesContextFactory, context.fileSystem(), linesOfCode));
        scanner = OneCAstScanner.create(conf, visitors.toArray(new SquidAstVisitor[visitors.size()]));
        FilePredicates p = context.fileSystem().predicates();
        scanner.scanFiles(Lists.newArrayList(context.fileSystem().files(p.and(p.hasType(InputFile.Type.MAIN), p.hasLanguage(OneC.KEY)))));

        Collection<SourceCode> squidSourceFiles = scanner.getIndex().search(new QueryByType(SourceFile.class));
        save(squidSourceFiles);
        /*savePreciseIssues(
            visitors
                .stream()
                .filter(v -> v instanceof OneCCheck)
                .map(v -> (OneCCheck) v)
                .collect(Collectors.toList()));
*/

    }

    private void save(Collection<SourceCode> squidSourceFiles) {
        for (SourceCode squidSourceFile : squidSourceFiles) {
            SourceFile squidFile = (SourceFile) squidSourceFile;

            InputFile inputFile = context.fileSystem().inputFile(context.fileSystem().predicates().is(new java.io.File(squidFile.getKey())));

            //noSonarFilter.noSonarInFile(inputFile, squidFile.getNoSonarTagLines());

            //saveFilesComplexityDistribution(inputFile, squidFile);
            //saveFunctionsComplexityDistribution(inputFile, squidFile);
            saveMeasures(inputFile, squidFile);
            saveIssues(inputFile, squidFile);
        }
    }

    private void saveMeasures(InputFile inputFile, SourceFile squidFile) {
        saveMetricOnFile(inputFile, CoreMetrics.NCLOC, squidFile.getInt(OneCMetric.LINES_OF_CODE));
        /*saveMetricOnFile(inputFile, CoreMetrics.STATEMENTS, squidFile.getInt(OnecMetric.STATEMENTS));
        saveMetricOnFile(inputFile, CoreMetrics.FUNCTIONS, squidFile.getInt(OnecMetric.FUNCTIONS));
        saveMetricOnFile(inputFile, CoreMetrics.CLASSES, squidFile.getInt(OnecMetric.CLASSES));
        saveMetricOnFile(inputFile, CoreMetrics.COMPLEXITY, squidFile.getInt(OnecMetric.COMPLEXITY));
        */
        saveMetricOnFile(inputFile, CoreMetrics.COMMENT_LINES, squidFile.getInt(OneCMetric.COMMENT_LINES));
    }

    private <T extends Serializable> void saveMetricOnFile(InputFile inputFile, Metric metric, T value) {
        context.<T>newMeasure()
        .withValue(value)
        .forMetric(metric)
        .on(inputFile)
        .save();
    }
    private void saveIssues(InputFile inputFile, SourceFile squidFile) {
        Collection<CheckMessage> messages = squidFile.getCheckMessages();
        for (CheckMessage message : messages) {
            RuleKey ruleKey = checks.ruleKey((SquidAstVisitor<Grammar>) message.getCheck());
            NewIssue newIssue = context.newIssue();

            NewIssueLocation primaryLocation = newIssue.newLocation()
            .message(message.getText(Locale.ENGLISH))
            .on(inputFile);

            if (message.getLine() != null) {
            primaryLocation.at(inputFile.selectLine(message.getLine()));
            }

            newIssue.forRule(ruleKey).at(primaryLocation).save();
        }
    }

    void analyseFiles(SensorContext context, List<InputFile> inputFiles, ProgressReport progressReport){
        
        boolean success = false;
        
        try {
        
            for (InputFile inputFile : inputFiles) {
                progressReport.nextFile();
                analyseFile(context, inputFile);
            }

            success = true;
            
        } finally {
            stopProgressReport(progressReport, success);
        }
       
    }

    private void analyseFile(SensorContext context, InputFile inputFile) {
        LOG.info("analyse file '" + inputFile.absolutePath() + "'\n"
            + "\t language: " + inputFile.language() + "\n"
            + "\t charset: " + inputFile.charset());
    }

    private static void stopProgressReport(ProgressReport progressReport, boolean success) {
        if (success) {
            progressReport.stop();
        } else {
            progressReport.cancel();
        }
    }

    private OneCConfiguration createConfiguration() {
        return new OneCConfiguration(context.fileSystem().encoding());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
