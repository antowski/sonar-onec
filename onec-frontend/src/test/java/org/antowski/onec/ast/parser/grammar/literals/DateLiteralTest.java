package org.antowski.onec.ast.parser.grammar.literals;

import org.antowski.onec.ast.api.OneCTokenType;
import org.junit.Test;

import static org.antowski.onec.utils.Assertions.assertThat;

public class DateLiteralTest {

    @Test
    public void realLife() {
        assertThat(OneCTokenType.DATE_LITERAL)

                .matches("''")
                .matches("'01.01.2000'")

                // all not valid date expressions between '' is empty date
                .matches("'32.-7.123456'")

                .notMatches("date: '01.01.2000'")

                .notMatches("'01.01.2000''");

    }

}
