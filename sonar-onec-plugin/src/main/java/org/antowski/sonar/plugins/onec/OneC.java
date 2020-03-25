package org.antowski.sonar.plugins.onec;

import java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.sonar.api.config.Configuration;
import org.sonar.api.resources.AbstractLanguage;

public class OneC extends AbstractLanguage {

  static final String KEY = "1c 7.7";

  private static final String[] DEFAULT_FILE_SUFFIXES = { ".1s" };

  private Configuration config;

  OneC(Configuration config) {
    super(KEY, "1C:Enterprise 7.7");
    this.config = config;
  }

  @Override
  public String[] getFileSuffixes() {
    String[] suffixes = Arrays.stream(config.getStringArray(OneCPlugin.FILE_SUFFIXES_KEY))
            .filter(StringUtils::isNotBlank).toArray(String[]::new);
    return suffixes.length == 0 ? OneC.DEFAULT_FILE_SUFFIXES : suffixes;
  }

}
