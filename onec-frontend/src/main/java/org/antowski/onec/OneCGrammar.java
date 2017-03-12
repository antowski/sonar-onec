
package org.antowski.onec;

import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerfulGrammarBuilder;

import static com.sonar.sslr.api.GenericTokenType.EOF;

public enum OneCGrammar implements GrammarRuleKey {

    FILE_INPUT;

    public static LexerfulGrammarBuilder create() {
        
        LexerfulGrammarBuilder b = LexerfulGrammarBuilder.create();

        b.rule(FILE_INPUT).is(
            b.zeroOrMore(b.anyTokenButNot(EOF)), // Consume all tokens types except EOF
            EOF); // Expect an end of file

        b.setRootRule(FILE_INPUT);

        return b;

    }
}
