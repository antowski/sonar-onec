
package org.antowski.sonar.plugins.onec;

import org.antowski.onec.checks.CheckList;

import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.utils.ValidationMessages;
import org.sonar.squidbridge.annotations.AnnotationBasedProfileBuilder;

/**
 * Default Quality profile for the projects having files of language "1C 7.7"
 */
public final class OneCQualityProfile extends ProfileDefinition {

    private final RuleFinder ruleFinder;

    public OneCQualityProfile(RuleFinder ruleFinder) {
        this.ruleFinder = ruleFinder;
    }

    @Override
    public RulesProfile createProfile(ValidationMessages validation) {
        AnnotationBasedProfileBuilder annotationBasedProfileBuilder = new AnnotationBasedProfileBuilder(ruleFinder);
        return annotationBasedProfileBuilder.build(
                CheckList.REPOSITORY_KEY,
                CheckList.MAIN_PROFILE,
                OneC.KEY,
                CheckList.getChecks(),
                validation);
    }
}
