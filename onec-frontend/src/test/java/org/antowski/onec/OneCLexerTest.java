
package org.antowski.onec;

import com.google.common.base.Charsets;

import com.sonar.sslr.impl.Lexer;

import static com.sonar.sslr.test.lexer.LexerMatchers.hasComment;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class OneCLexerTest {

    private static Lexer lexer;

    @BeforeClass
    public static void init() {
        lexer = OneCLexer.create(new OneCConfiguration(Charsets.UTF_8));
    }

    @Test
    public void comments() {
        assertThat(lexer.lex("// my comment \n new line"), hasComment("// my comment "));
        assertThat(lexer.lex("// мой комментарий \n new line"), hasComment("// мой комментарий "));
        assertThat(lexer.lex("В одной строке код // и комментарий \n следующая строка"), hasComment("// и комментарий "));
        assertThat(lexer.lex("Много слешей подряд = один комментарий ////////////"), hasComment("////////////"));
    }

}
