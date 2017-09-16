package org.antowski.onec.ast.parser.grammar.literals;

import org.antowski.onec.ast.parser.OneCLexer;
import org.junit.Test;

import static org.antowski.onec.utils.Assertions.assertThat;

public class LiteralTest {

    @Test
    public void realLife() {
        assertThat(OneCLexer.LITERAL)
                .matches("1.0")
                .matches("1")
                .matches("\"string - это строка\"")
                .matches("'01.04.2017'")
                .matches("'1000.-1.888'");
    }

}
