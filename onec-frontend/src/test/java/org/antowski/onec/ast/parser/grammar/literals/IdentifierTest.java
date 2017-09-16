package org.antowski.onec.ast.parser.grammar.literals;

import org.antowski.onec.ast.api.OneCTokenType;
import org.junit.Test;

import static org.antowski.onec.utils.Assertions.assertThat;

public class IdentifierTest {

    @Test
    public void realLife() {
        assertThat(OneCTokenType.IDENTIFIER)
                .matches("q")
                .matches("й")
                .matches("МояПеременная")
                .matches("__var")
                .matches("_1")

                .matches("var123")

                .notMatches("123var")

                .notMatches("перем")
                .notMatches("функция")
                .notMatches("procedure")
                .notMatches("for")
                .notMatches("if");
    }

}
