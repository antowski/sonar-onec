package org.antowski.onec;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OneCMetricTest {

    @Test
    public void test() {
        assertThat(OneCMetric.values()).hasSize(3);

        for (OneCMetric metric : OneCMetric.values()) {
            assertThat(metric.getName()).isEqualTo(metric.name());
            assertThat(metric.isCalculatedMetric()).isFalse();
            assertThat(metric.aggregateIfThereIsAlreadyAValue()).isTrue();
            assertThat(metric.isThereAggregationFormula()).isTrue();
            assertThat(metric.getCalculatedMetricFormula()).isNull();
        }
    }

}
