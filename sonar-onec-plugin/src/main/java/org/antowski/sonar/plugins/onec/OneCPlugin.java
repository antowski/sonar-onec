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
      OneCSensor.class,
      OneCQualityProfile.class
      
      );
   
  }

}
