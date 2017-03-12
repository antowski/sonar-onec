package org.antowski.sonar.plugins.onec;

import com.google.common.collect.Lists;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.AbstractLanguage;

public class OneC extends AbstractLanguage {

  public static final String KEY = "1c 7.7";

  private static final String[] DEFAULT_FILE_SUFFIXES = { ".1s" };

  private Settings settings;

  public OneC(Settings settings) {
    super(KEY, "1C:Enterprise 7.7");
    this.settings = settings;
  }

  @Override
  public String[] getFileSuffixes() {
    String[] suffixes = filterEmptyStrings(settings.getStringArray(OneCPlugin.FILE_SUFFIXES_KEY));
    return suffixes.length == 0 ? OneC.DEFAULT_FILE_SUFFIXES : suffixes;
  }

  private static String[] filterEmptyStrings(String[] stringArray) {
    List<String> nonEmptyStrings = Lists.newArrayList();
    for (String string : stringArray) {
      if (StringUtils.isNotBlank(string.trim())) {
        nonEmptyStrings.add(string.trim());
      }
    }
    return nonEmptyStrings.toArray(new String[nonEmptyStrings.size()]);
  }

}
