package org.antowski.onec.ast.parser.grammar.literals;

import org.antowski.onec.ast.api.OneCTokenType;
import org.junit.Test;

import static org.antowski.onec.utils.Assertions.assertThat;

public class StringLiteralTest {

    @Test
    public void simple() {
        assertThat(OneCTokenType.STRING_LITERAL)

                .as("empty string").matches("\"\"")
                .as("string with number").matches("\"100\"")
                .as("string with date").matches("\"'01.01.2001'\"")
                .as("regular string").matches("\"Тут был Вася\"")

                .notMatches("\"")
                .notMatches("125");

    }

    @Test
    public void multiline() {

        assertThat(OneCTokenType.STRING_LITERAL)

                .as("space between").matches("\"first\" \"second\"")
                .as("tab between").matches("\"first\"\t\"second\"")
                .as("spaces and tab between").matches("\"first\"  \t \"second\"")
                .as("new line").matches("\"first\"\n\"second\"")
                .as("new line with spaces").matches("\"first\"  \n \"second\"")

                .as("with quotes").matches("\"Самый \"\"трудолюбивый\"\" мальчик\"")

                .notMatches("\"Нечетное \"\"количество\"\"\" двойных кавычек");

    }

}
