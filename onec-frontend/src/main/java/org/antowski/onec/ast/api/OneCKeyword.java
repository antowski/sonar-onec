package org.antowski.onec.ast.api;

import org.sonar.sslr.grammar.GrammarRuleKey;

public enum OneCKeyword implements GrammarRuleKey {

    AND("And","И"),
    OR("Or","ИЛИ"),
    NOT("Not","Не"),

    IF("if","Если"),
    THEN("then","Тогда"),
    ELSIF("Elsif","ИначеЕсли"),
    ELSE("Else","Иначе"),
    ENDIF("Endlf","КонецЕсли"),

    WHILE("While","Пока"),
    FOR("For","Для"),
    TO("To","По"),
    DO("Do","Цикл"),
    ENDDO("EndDo","КонецЦикла"),

    PROCEDURE("Procedure","Процедура"),
    ENDPROCEDURE("EndProcedure","КонецПроцедуры"),
    FUNCTION("Function","Функция"),
    ENDFUNCTION("EndFunction","КонецФункции"),

    VAR("Var","Перем"),
    VAL("Val","Знач"),
    EXPORT("Export","Экспорт"),
    CONTEXT("Context","Контекст"),

    GOTO("Goto","Перейти"),
    RETURN("Return","Возврат"),
    CONTINUE("Continue","Продолжить"),
    BREAK("Break","Прервать");

    private final String EnValue;
    private final String RuValue;

    OneCKeyword(String EnValue, String RuValue) {
        this.EnValue = EnValue;
        this.RuValue = RuValue;
    }

    public String getName() {
        return name();
    }

    public String getEnValue() {
        return EnValue;
    }

    public String getRuValue() {
        return RuValue;
    }

    public static String[] keywordValues() {
        OneCKeyword[] keywordsEnum = OneCKeyword.values();
        String[] keywords = new String[2 * keywordsEnum.length];
        for (int i = 0; i < keywordsEnum.length; i++) {
            keywords[i] = keywordsEnum[i].getEnValue();
            keywords[keywordsEnum.length + i] = keywordsEnum[i].getRuValue();
        }
        return keywords;
    }

}
