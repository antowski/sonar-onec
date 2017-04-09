package org.antowski.onec.parser.grammar.literals;

import org.antowski.onec.ast.api.OneCTokenType;
import org.junit.Test;

import static org.antowski.onec.utils.Assertions.assertThat;

public class StringLiteralTest {

    @Test
    public void realLife() {
        assertThat(OneCTokenType.STRING_LITERAL)

                .as("empty string").matches("\"\"")
                .as("string with number").matches("\"100\"")
                .as("string with date").matches("\"'01.01.2001'\"")
                .as("regular string").matches("\"Тут был Вася\"")

                .notMatches("\"")
                .notMatches("125");

    }

}
