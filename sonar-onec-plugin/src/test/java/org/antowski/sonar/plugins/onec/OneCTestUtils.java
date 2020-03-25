package org.antowski.sonar.plugins.onec;

import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.sensor.internal.SensorContextTester;

import java.io.File;
import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;

public class OneCTestUtils {

    private OneCTestUtils() {
    }

    public static DefaultFileSystem getDefaultFileSystem() {
        return new DefaultFileSystem(getModuleBaseDir());
    }

    public static File getModuleBaseDir() {
        return new File("src/test/resources");
    }

    public static <T extends Serializable> void assertMeasure(SensorContextTester context, String componentKey, org.sonar.api.measures.Metric<T> metric, T expected) {
        assertThat(context.measure(componentKey, metric).value()).as("metric for: " + metric.getKey()).isEqualTo(expected);
    }

    public static <T extends Serializable> void assertNoMeasure(SensorContextTester context, String componentKey, org.sonar.api.measures.Metric<T> metric) {
        assertThat(context.measure(componentKey, metric)).as("metric for: " + metric.getKey()).isNull();
    }

}
