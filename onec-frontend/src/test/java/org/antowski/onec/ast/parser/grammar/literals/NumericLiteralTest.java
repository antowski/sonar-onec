package org.antowski.onec.ast.parser.grammar.literals;

import org.antowski.onec.ast.api.OneCTokenType;
import org.junit.Test;

import static org.antowski.onec.utils.Assertions.assertThat;

public class NumericLiteralTest {

    @Test
    public void realLife() {
        assertThat(OneCTokenType.NUMERIC_LITERAL)

                .matches("0")
                .matches("3")
                .matches("1234567890")
                .matches("123.34")

                .notMatches("")
                .notMatches("'100'")
                .notMatches("\"100\"")
                .notMatches("1.2.3")
                .notMatches(".5");

    }

}
