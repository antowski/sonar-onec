/*
 * SonarQube 1C:Enterprise 7.7 Plugin
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

import org.sonar.api.Plugin;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

/**
 * This class is the entry point for all extensions. It is referenced in pom.xml.
 */
public class OneCPlugin implements Plugin {

  private static final String GENERAL = "General";

  public static final String FILE_SUFFIXES_KEY = "antowski.onec.file.suffixes";

  @Override
  public void define(Context context) {
    
    context.addExtensions(

      PropertyDefinition.builder(FILE_SUFFIXES_KEY)
        .name("File Suffixes")
        .description("Comma-separated list of suffixes of 1C 7.7 Enterprise files to analyze.")
        .subCategory(GENERAL)
        .onQualifiers(Qualifiers.PROJECT)
        .defaultValue("1s")
        .build(),

      OneC.class,
      OneCSquidSensor.class,
      OneCQualityProfile.class
      
      );
   
  }

}
