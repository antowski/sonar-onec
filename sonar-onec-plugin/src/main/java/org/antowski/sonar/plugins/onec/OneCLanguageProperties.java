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

import static java.util.Arrays.asList;

import java.util.List;

import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

public class OneCLanguageProperties {

  public static final String FILE_SUFFIXES_KEY = "antowski.onec.file.suffixes";
  public static final String FILE_SUFFIXES_DEFAULT_VALUE = ".1s";

  private OneCLanguageProperties() {
    // only statics
  }

  public static List<PropertyDefinition> getProperties() {
    return asList(PropertyDefinition.builder(FILE_SUFFIXES_KEY)
      .defaultValue(FILE_SUFFIXES_DEFAULT_VALUE)
      .category("1c7")
      .name("File Suffixes")
      .description("Comma-separated list of suffixes for files to analyze.")
      .onQualifiers(Qualifiers.PROJECT)
      .build());
  }

}
