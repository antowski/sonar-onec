package org.antowski.onec.ast.api;

import org.sonar.sslr.grammar.GrammarRuleKey;

/**
 * Type of tokens for 1c 7.7 grammar.
 */

public enum OneCTokenType implements GrammarRuleKey {

    CHARACTER_LITERAL,
    NUMERIC_LITERAL,
    DATE_LITERAL,
    STRING_LITERAL,
    IDENTIFIER;

    public String getName() {
        return name();
    }

    public String getValue() {
        return name();
    }

}
