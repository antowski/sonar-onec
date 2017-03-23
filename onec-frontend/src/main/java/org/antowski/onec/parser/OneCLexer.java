package org.antowski.onec.parser;

import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

public enum OneCLexer implements GrammarRuleKey {

    COMPILATION_UNIT,

    EOF;

    public static LexerlessGrammarBuilder createGrammarBuilder() {
        LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();

        /*
        punctuators(b);
        keywords(b);

        literals(b);
        */

        b.setRootRule(COMPILATION_UNIT);

        return b;
    }

}
