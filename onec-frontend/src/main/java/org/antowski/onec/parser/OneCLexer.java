package org.antowski.onec.parser;

import com.sonar.sslr.api.GenericTokenType;
import org.antowski.onec.ast.api.OneCKeyword;
import org.sonar.api.internal.apachecommons.lang.ArrayUtils;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import java.util.Arrays;

import static org.antowski.onec.ast.api.OneCTokenType.DATE_LITERAL;
import static org.antowski.onec.ast.api.OneCTokenType.NUMERIC_LITERAL;
import static org.antowski.onec.ast.api.OneCTokenType.STRING_LITERAL;
import static org.antowski.onec.ast.api.OneCTokenType.IDENTIFIER;

import static org.antowski.onec.ast.api.OneCPunctuator.COLON;
import static org.antowski.onec.ast.api.OneCPunctuator.COMMA;
import static org.antowski.onec.ast.api.OneCPunctuator.DOT;
import static org.antowski.onec.ast.api.OneCPunctuator.SEMICOLON;
import static org.antowski.onec.ast.api.OneCPunctuator.TILDA;

import static org.antowski.onec.ast.api.OneCPunctuator.EQUAL;
import static org.antowski.onec.ast.api.OneCPunctuator.NOTEQUAL;
import static org.antowski.onec.ast.api.OneCPunctuator.GE;
import static org.antowski.onec.ast.api.OneCPunctuator.GT;
import static org.antowski.onec.ast.api.OneCPunctuator.LE;
import static org.antowski.onec.ast.api.OneCPunctuator.LT;

import static org.antowski.onec.ast.api.OneCPunctuator.LBRK;
import static org.antowski.onec.ast.api.OneCPunctuator.RBRK;
import static org.antowski.onec.ast.api.OneCPunctuator.LPAR;
import static org.antowski.onec.ast.api.OneCPunctuator.RPAR;

import static org.antowski.onec.ast.api.OneCPunctuator.PLUS;
import static org.antowski.onec.ast.api.OneCPunctuator.MINUS;
import static org.antowski.onec.ast.api.OneCPunctuator.STAR;
import static org.antowski.onec.ast.api.OneCPunctuator.DIV;
import static org.antowski.onec.ast.api.OneCPunctuator.MOD;

import static org.antowski.onec.ast.api.OneCPunctuator.QUERY;

public enum OneCLexer implements GrammarRuleKey {

    COMPILATION_UNIT,

    /**
     * Lexical
     */
    EOF,
    KEYWORD,

    /**
     * Declaration
     */
    GLOBAL_VARIABLE_DECLARATION,
    LOCAL_VARIABLE_DECLARATION,

    /**
     * SPACING
     */
    SPACING,

    WHITESPACES

    ;

    public static LexerlessGrammarBuilder createGrammarBuilder() {

        LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();

        punctuators(b);
        keywords(b);
        literals(b);

        b.setRootRule(COMPILATION_UNIT);

        return b;

    }

    private static void punctuators(LexerlessGrammarBuilder b) {

        punctuator(b, COLON, ":");
        punctuator(b, COMMA, ",");
        punctuator(b, DOT, ".");
        punctuator(b, SEMICOLON, ";");
        punctuator(b, TILDA, "~");

        punctuator(b, EQUAL, "=");
        punctuator(b, NOTEQUAL, "<>");
        punctuator(b, GE, ">=");
        punctuator(b, GT, ">", b.nextNot("="));
        punctuator(b, LE, "<=");
        punctuator(b, LT, "<", b.nextNot(b.firstOf("=", ">")));

        punctuator(b, LBRK, "[");
        punctuator(b, RBRK, "]");
        punctuator(b, LPAR, "(");
        punctuator(b, RPAR, ")");

        punctuator(b, PLUS, "+");
        punctuator(b, MINUS, "-");
        punctuator(b, STAR, "*");
        punctuator(b, DIV, "/");
        punctuator(b, MOD, "%");

        punctuator(b, QUERY, "?");

    }

    private static void punctuator(LexerlessGrammarBuilder b, GrammarRuleKey ruleKey, String value) {
        b.rule(ruleKey).is(value, SPACING);
    }

    private static void punctuator(LexerlessGrammarBuilder b, GrammarRuleKey ruleKey, String value, Object element) {
        b.rule(ruleKey).is(value, element, SPACING);
    }

    private static void keywords(LexerlessGrammarBuilder b) {

        for (int i = 0; i < OneCKeyword.values().length; i++) {
            OneCKeyword tokenType = OneCKeyword.values()[i];

            // 1C keywords are case insensitive
            b.rule(tokenType).is(SPACING,
                    b.regexp("(?iu)" + tokenType.getEnValue() + "|" + tokenType.getRuValue()),
                    b.nextNot(b.regexp(LexicalConstant.IDENTIFIER_PART))).skip();
        }

        OneCKeyword[] keywords = OneCKeyword.values();
        Arrays.sort(keywords);
        ArrayUtils.reverse(keywords);

        b.rule(KEYWORD).is(SPACING,
                b.firstOf(
                        keywords[0],
                        keywords[1],
                        ArrayUtils.subarray(keywords, 2, keywords.length)),
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

        b.rule(EOF).is(b.token(GenericTokenType.EOF, b.endOfInput()));

        b.rule(WHITESPACES).is(b.regexp("[" + LexicalConstant.WHITESPACE + "]*+"));

        b.rule(IDENTIFIER).is(SPACING, b.nextNot(KEYWORD), b.regexp(LexicalConstant.IDENTIFIER));

        b.rule(DATE_LITERAL).is(b.regexp(LexicalConstant.DATE_LITERAL), SPACING);
        b.rule(NUMERIC_LITERAL).is(b.regexp(LexicalConstant.NUMERIC_LITERAL), SPACING);
        b.rule(STRING_LITERAL).is(b.regexp(LexicalConstant.STRING_LITERAL), SPACING);

    }

    private static Object whitespace(LexerlessGrammarBuilder b) {
        return b.regexp("\\s*+");
    }

    private static Object comment(LexerlessGrammarBuilder b) {
        return b.regexp("//[^\\n\\r]*+");
    }

}
