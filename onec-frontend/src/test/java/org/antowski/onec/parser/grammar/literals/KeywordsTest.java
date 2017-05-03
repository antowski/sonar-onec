package org.antowski.onec.parser.grammar.literals;

import org.antowski.onec.parser.OneCLexer;
import org.junit.Test;

import static org.antowski.onec.utils.Assertions.assertThat;

public class KeywordsTest {

    @Test
    public void test() {
        assertThat(OneCLexer.KEYWORD)
                .matches("if")
                .matches("then")
                .matches("Тогда")
                .matches("Если")
                .matches("ЕслИ")
                .matches("ИНАЧЕЕсли")

                .notMatches("_ИНАЧЕЕсли")
                .notMatches("ИначеIf")

                .matches("Иначе")
                .matches("КонецЕсли")
                .matches("Цикл")
                .matches("Для")
                .matches("По")
                .matches("Пока")
                .matches("Функция")
                .matches("КонецПроцедуры")
                .matches("КонецФункции")

                .matches("Перем")
                .matches("Перейти")
                .matches("Знач")
                .matches("Контекст")
                .matches("Экспорт")

                .matches("И")
                .matches("ИЛИ")
                .matches("НЕ")

                .matches("Продолжить")
                .matches("Прервать")
                .matches("Возврат")

                ;
    }

}
