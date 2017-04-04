package org.antowski.onec.ast.api;

import org.sonar.sslr.grammar.GrammarRuleKey;

public enum OneCKeyword implements GrammarRuleKey {

    IF("if","Если"),
    THEN("then","Тогда"),
    ELSIF("Elsif","ИначеЕсли");

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
