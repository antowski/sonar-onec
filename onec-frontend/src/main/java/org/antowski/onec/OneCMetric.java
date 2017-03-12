
package org.antowski.onec;

import org.sonar.squidbridge.measures.CalculatedMetricFormula;
import org.sonar.squidbridge.measures.MetricDef;

public enum OneCMetric implements MetricDef {
    FILES,
    LINES_OF_CODE,
    /*STATEMENTS,
    FUNCTIONS,
    CLASSES,
    COMPLEXITY,*/
    COMMENT_LINES;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public boolean isCalculatedMetric() {
        return false;
    }

    @Override
    public boolean aggregateIfThereIsAlreadyAValue() {
        return true;
    }

    @Override
    public boolean isThereAggregationFormula() {
        return true;
    }

    @Override
    public CalculatedMetricFormula getCalculatedMetricFormula() {
        return null;
    }

}
