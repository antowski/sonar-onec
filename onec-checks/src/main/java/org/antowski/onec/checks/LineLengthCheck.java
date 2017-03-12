package org.antowski.onec.checks;

import com.sonar.sslr.api.AstAndTokenVisitor;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.api.Token;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.checks.SquidCheck;

@Rule(
    key = LineLengthCheck.CHECK_KEY,
    name = "Lines should not be too long",
    priority = Priority.MINOR
)
@SqaleConstantRemediation("1min")
public class LineLengthCheck extends SquidCheck<Grammar> implements AstAndTokenVisitor {

  public static final String CHECK_KEY = "LineLength";
  private static final int DEFAULT_MAXIMUM_LINE_LENGTH = 120;

  private Token previousToken;

  @RuleProperty(
    key = "maximumLineLength",
    defaultValue = "" + DEFAULT_MAXIMUM_LINE_LENGTH)
  public int maximumLineLength = DEFAULT_MAXIMUM_LINE_LENGTH;

  public int getMaximumLineLength() {
    return maximumLineLength;
  }

  @Override
  public void visitFile(AstNode astNode) {
    previousToken = null;
  }

  @Override
  public void leaveFile(AstNode astNode) {
    previousToken = null;
  }

  @Override
  public void visitToken(Token token) {
    if (token.isGeneratedCode()) {
      return;
    }

    if (previousToken != null && previousToken.getLine() != token.getLine()) {
      // Note that AbstractLineLengthCheck doesn't support tokens which span multiple lines - see SONARPLUGINS-2025
      String[] lines = previousToken.getValue().split("\r?\n|\r", -1);
      int length = previousToken.getColumn();
      for (String line : lines) {
        length += line.length();
        if (length > getMaximumLineLength()) {
          // Note that method from AbstractLineLengthCheck generates other message - see SONARPLUGINS-1809
          getContext().createLineViolation(this,
            "The line contains {0,number,integer} characters which is greater than {1,number,integer} authorized.",
            previousToken.getLine(),
            length,
            getMaximumLineLength());
        }
        length = 0;
      }
    }
    previousToken = token;
  }

}
