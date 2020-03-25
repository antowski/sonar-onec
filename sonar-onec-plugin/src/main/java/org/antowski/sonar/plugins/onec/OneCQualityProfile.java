package org.antowski.sonar.plugins.onec;

import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;

/**
 * Default Quality profile for the projects having files of language "1C 7.7"
 */
public final class OneCQualityProfile implements BuiltInQualityProfilesDefinition {

    @Override
    public void define(Context context) {
        NewBuiltInQualityProfile main = context.createBuiltInQualityProfile("Main", OneC.KEY);
        main.activateRule("common-" + OneC.KEY, "DuplicatedBlocks");
        // TODO SecurityRuleKeys for test
        main.done();
    }

}
