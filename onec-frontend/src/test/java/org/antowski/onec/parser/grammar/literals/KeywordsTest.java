package org.antowski.onec.parser.grammar.literals;

import org.antowski.onec.ast.api.OneCKeyword;
import org.antowski.onec.parser.OneCLexer;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.antowski.onec.utils.Assertions.assertThat;

/**
 * Created by antowski on 03.04.2017.
 */
public class KeywordsTest {

    @Test
    public void test() {
        assertThat(OneCLexer.KEYWORD)
                .matches("if")
                .matches("Если")
                .matches("Тогда")
                .matches("ИначеЕсли")
                .matches("Иначе")
                .matches("КонецЕсли")
                .matches("Цикл")
                .matches("Для")
                .matches("По")
                .matches("Пока")
                .matches("Функция")
                .matches("КонецПроцедуры")
                .matches("КонецФункции")
                .matches("Предупреждение")
                ;
    }

}
