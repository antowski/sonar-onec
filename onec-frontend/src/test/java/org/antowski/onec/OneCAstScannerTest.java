package org.antowski.onec;

import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;

import static org.assertj.core.api.Assertions.assertThat;

public class OneCAstScannerTest {

  @Test
  public void files() {
    SourceFile file = OneCAstScanner.scanSingleFile("src/test/resources/file.1s");
    assertThat(file.getInt(OneCMetric.FILES)).isEqualTo(1);
  }

  @Test
  public void LinesOfCode() {
    SourceFile file = OneCAstScanner.scanSingleFile("src/test/resources/file.1s");
    //assertThat(file.getInt(OneCMetric.LINES_OF_CODE)).isEqualTo(5);
  }

  @Test
  public void Comments() {
    SourceFile file = OneCAstScanner.scanSingleFile("src/test/resources/file.1s");
    //assertThat(file.getInt(OneCMetric.COMMENT_LINES)).isEqualTo(2);
  }

}
