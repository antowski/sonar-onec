package org.antowski.onec.ast.api;

import org.sonar.sslr.grammar.GrammarRuleKey;

public enum OneCPunctuator implements GrammarRuleKey {

    COLON(":"),
    COMMA(","),
    DOT("."),
    SEMICOLON(";"),
    TILDA("~"),

    EQUAL("="),
    NOTEQUAL("<>"),
    GE(">="),
    GT(">"),
    LE("<="),
    LT("<"),

    LBRK("["),
    RBRK("]"),
    LPAR("("),
    RPAR(")"),

    PLUS("+"),
    MINUS("-"),
    STAR("*"),
    DIV("/"),
    MOD("%"),

    VLINE("|"),
    QUERY("?"),

    ;

    private final String value;

    OneCPunctuator(String word) {
        this.value = word;
    }

    public String getName() {
        return name();
    }

    public String getValue() {
        return value;
    }

}
