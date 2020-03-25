package org.antowski.onec.checks;

import com.google.common.collect.ImmutableList;

public final class CheckList {

  public static final String REPOSITORY_KEY = "1C 7.7";

  public static final String MAIN_PROFILE = "main";

  private CheckList() {
  }

  public static Iterable<Class> getChecks() {
    return ImmutableList.<Class>of(
    );
  }

}
