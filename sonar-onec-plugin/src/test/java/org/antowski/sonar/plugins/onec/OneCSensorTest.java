package org.antowski.sonar.plugins.onec;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.antowski.onec.checks.CheckList;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.FileMetadata;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.internal.ActiveRulesBuilder;
import org.sonar.api.batch.sensor.internal.DefaultSensorDescriptor;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.internal.google.common.base.Charsets;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.rule.RuleKey;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OneCSensorTest {

    private final File baseDir = new File("src/test/resources");
    private final SensorContextTester context = SensorContextTester.create(OneCTestUtils.getModuleBaseDir());

    private CheckFactory checkFactory = new CheckFactory(mock(ActiveRules.class));

    private FileLinesContextFactory createFileLinesContextFactory() {
        FileLinesContextFactory fileLinesContextFactory = mock(FileLinesContextFactory.class);
        FileLinesContext fileLinesContext = mock(FileLinesContext.class);
        when(fileLinesContextFactory.createFor(any(InputFile.class))).thenReturn(fileLinesContext);
        return fileLinesContextFactory;
    }

    private OneCSensor createSensor() {
        return new OneCSensor(context.fileSystem(), createFileLinesContextFactory(), checkFactory);
    }

    @Test
    public void sensor_descriptor() {
        DefaultSensorDescriptor descriptor = new DefaultSensorDescriptor();
        createSensor().describe(descriptor);

        assertThat(descriptor.name()).isEqualTo("1C 7.7 sensor");
        assertThat(descriptor.languages()).containsOnly("1c 7.7");
        assertThat(descriptor.type()).isEqualTo(Type.MAIN);
    }

    @Ignore
    @Test
    public void analyse() throws NoSuchFieldException, IllegalAccessException {
        String fileName = "OneCSquidSensor.1s";
        String componentKey = "moduleKey:" + fileName;

        OneCSensor oneCSensor = createSensor();
        analyseSingleFile(oneCSensor, fileName);

        OneCTestUtils.assertMeasure(context, componentKey, CoreMetrics.LINES, 17);
        OneCTestUtils.assertMeasure(context, componentKey, CoreMetrics.NCLOC, 8);

        /*
        OneCTestUtils.assertMeasure(context, componentKey, CoreMetrics.COMPLEXITY_IN_CLASSES, 7);
        OneCTestUtils.assertMeasure(context, componentKey, CoreMetrics.COMPLEXITY_IN_FUNCTIONS, 10);
        OneCTestUtils.assertMeasure(context, componentKey, CoreMetrics.COMMENT_LINES, 7);
        OneCTestUtils.assertMeasure(context, componentKey, CoreMetrics.COMPLEXITY, 12);
        OneCTestUtils.assertMeasure(context, componentKey, CoreMetrics.CLASSES, 1);
        OneCTestUtils.assertMeasure(context, componentKey, CoreMetrics.STATEMENTS, 16);
        OneCTestUtils.assertMeasure(context, componentKey, CoreMetrics.FUNCTIONS, 3);
        OneCTestUtils.assertMeasure(context, componentKey, CoreMetrics.FUNCTION_COMPLEXITY_DISTRIBUTION, "1=0;2=2;4=1;6=0;8=0;10=0;12=0");
        OneCTestUtils.assertMeasure(context, componentKey, CoreMetrics.FILE_COMPLEXITY_DISTRIBUTION, "0=0;5=0;10=1;20=0;30=0;60=0;90=0");
        */

        // the .1s file contains NOSONAR at line 34
        //checkNoSonar(componentKey, 33, true, phpSensor);
        //checkNoSonar(componentKey, 34, false, phpSensor);
    }

    private void analyseSingleFile(OneCSensor sensor, String fileName) {
        context.fileSystem().add(inputFile(fileName));
        sensor.execute(context);
    }

    /*

    @Test
    public void testExecute() {
        
        activeRules = (new ActiveRulesBuilder())
            .create(RuleKey.of(CheckList.REPOSITORY_KEY, "PrintStatementUsage"))
            .setName("Print Statement Usage")
            .activate()
            .build();

        inputFile("OneCSquidSensor.1s");

        sensor().execute(context);

        String key = "moduleKey:OneCSquidSensor.1s";
        assertThat(context.measure(key, CoreMetrics.NCLOC).value()).isEqualTo(8);
        assertThat(context.measure(key, CoreMetrics.STATEMENTS).value()).isEqualTo(20);
        assertThat(context.measure(key, CoreMetrics.FUNCTIONS).value()).isEqualTo(4);
        assertThat(context.measure(key, CoreMetrics.CLASSES).value()).isEqualTo(1);
        assertThat(context.measure(key, CoreMetrics.COMPLEXITY).value()).isEqualTo(4);
        //assertThat(context.measure(key, CoreMetrics.COMMENT_LINES).value()).isEqualTo(3);

    }

    private OneCSensor sensor() {
        FileLinesContextFactory fileLinesContextFactory = mock(FileLinesContextFactory.class);
        FileLinesContext fileLinesContext = mock(FileLinesContext.class);
        when(fileLinesContextFactory.createFor(Mockito.any(InputFile.class))).thenReturn(fileLinesContext);
        CheckFactory checkFactory = new CheckFactory(activeRules);
        return new OneCSensor(fileLinesContextFactory, checkFactory);
    }
*/
    private DefaultInputFile inputFile(String fileName) {
        try {
            return TestInputFileBuilder.create("moduleKey", fileName)
                 .setModuleBaseDir(OneCTestUtils.getModuleBaseDir().toPath())
                 .setType(Type.MAIN)
                 .setCharset(Charset.defaultCharset())
                 .setLanguage(OneC.KEY)
                 .initMetadata(new String(java.nio.file.Files.readAllBytes(new File("src/test/resources/" + fileName).toPath()), StandardCharsets.UTF_8)).build();
        } catch (IOException e) {
            throw new IllegalStateException("File not found", e);
        }
    }

}
