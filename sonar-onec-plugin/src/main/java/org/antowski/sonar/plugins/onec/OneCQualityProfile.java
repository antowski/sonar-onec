/*
 * 1C:Enterprise 7.7 language plugin for SonarQube
 * Copyright (C) 2017 antowski
 * mailto:antowski AT gmail DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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
