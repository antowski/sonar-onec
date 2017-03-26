package org.antowski.onec.parser;

import com.sonar.sslr.api.GenericTokenType;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

public enum OneCLexer implements GrammarRuleKey {

    COMPILATION_UNIT,

    EOF,

    LETTER_OR_DIGIT,
    KEYWORD,
    SPACING;

    public static LexerlessGrammarBuilder createGrammarBuilder() {
        LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();

        /*
        punctuators(b);
        keywords(b);
        */

        literals(b);

        b.setRootRule(COMPILATION_UNIT);

        return b;
    }

    /**
     * Literals
     */
    private static void literals(LexerlessGrammarBuilder b) {
        b.rule(SPACING).is(
                b.skippedTrivia(whitespace(b)),
                b.zeroOrMore(
                        b.commentTrivia(comment(b)),
                        b.skippedTrivia(whitespace(b))));

        b.rule(EOF).is(b.token(GenericTokenType.EOF, b.endOfInput()));

    }

    private static Object whitespace(LexerlessGrammarBuilder b) {
        return b.regexp("\\s*+");
    }

    private static Object comment(LexerlessGrammarBuilder b) {
        return b.regexp("//[^\\n\\r]*+");
    }

}
