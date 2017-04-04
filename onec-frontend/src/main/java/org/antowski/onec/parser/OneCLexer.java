package org.antowski.onec.parser;

import com.sonar.sslr.api.GenericTokenType;
import org.antowski.onec.ast.api.OneCKeyword;
import org.antowski.onec.ast.api.OneCTokenType;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import java.util.Arrays;

public enum OneCLexer implements GrammarRuleKey {

    COMPILATION_UNIT,

    /**
     * Lexical
     */
    EOF,
    IDENTIFIER,
    KEYWORD,

    /**
     * SPACING
     */
    SPACING,

    WHITESPACES

    ;

    public static LexerlessGrammarBuilder createGrammarBuilder() {
        LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();

        //punctuators(b);
        keywords(b);

        literals(b);

        b.setRootRule(COMPILATION_UNIT);

        return b;
    }

    private static void keywords(LexerlessGrammarBuilder b) {
        Object[] rest = new Object[OneCKeyword.values().length - 2];

        for (int i = 0; i < OneCKeyword.values().length; i++) {
            OneCKeyword tokenType = OneCKeyword.values()[i];

            // 1C keywords are case insensitive
            b.rule(tokenType).is(SPACING,
                    b.regexp("(?i)" + tokenType.getEnValue() + "|" + tokenType.getRuValue()),
                    b.nextNot(b.regexp(LexicalConstant.IDENTIFIER_PART))).skip();
            if (i > 1) {
                rest[i - 2] = b.regexp("(?i)" + tokenType.getEnValue());
            }
        }

        b.rule(KEYWORD).is(SPACING,
                b.firstOf(
                        OneCKeyword.keywordValues()[0],
                        OneCKeyword.keywordValues()[1],
                        rest),
                b.nextNot(b.regexp(LexicalConstant.IDENTIFIER_PART))
        );
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

        b.rule(EOF).is(b.token(GenericTokenType.EOF, b.endOfInput())).skip();

        b.rule(WHITESPACES).is(b.regexp("[" + LexicalConstant.WHITESPACE + "]*+"));
        b.rule(OneCTokenType.IDENTIFIER).is(SPACING, b.nextNot(KEYWORD), b.regexp(LexicalConstant.IDENTIFIER));

    }

    private static Object whitespace(LexerlessGrammarBuilder b) {
        return b.regexp("\\s*+");
    }

    private static Object comment(LexerlessGrammarBuilder b) {
        return b.regexp("//[^\\n\\r]*+");
    }

}
