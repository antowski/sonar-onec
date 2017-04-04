package org.antowski.onec.utils;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.sonar.sslr.api.RecognitionException;
import com.sonar.sslr.api.Rule;
import com.sonar.sslr.api.typed.ActionParser;
import org.antowski.onec.model.InternalSyntaxToken;
import org.antowski.onec.model.OneCTree;
import org.antowski.onec.parser.OneCGrammar;
import org.antowski.onec.parser.OneCLexer;
import org.antowski.onec.parser.OneCNodeBuilder;
import org.antowski.onec.parser.TreeFactory;
import org.antowski.plugins.onec.api.tree.Tree;
import org.fest.assertions.GenericAssert;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import org.sonar.sslr.tests.ParsingResultComparisonFailure;
import org.sonar.sslr.tests.RuleAssert;

public class Assertions{

    public static RuleAssert assertThat(Rule actual) {
        return new RuleAssert(actual);
    }

    public static ParserAssert assertThat(GrammarRuleKey rule) {
        return assertThat(OneCLexer.createGrammarBuilder(), rule);
    }

    public static ParserAssert assertThat(LexerlessGrammarBuilder b, GrammarRuleKey rule) {
        return new ParserAssert(new ActionParser<Tree>(
                Charsets.UTF_8,
                b,
                OneCGrammar.class,
                new TreeFactory(),
                new OneCNodeBuilder(),
                rule));
    }

    public static class ParserAssert extends GenericAssert<ParserAssert, ActionParser<Tree>> {

        public ParserAssert(ActionParser<Tree> actual) {
            super(ParserAssert.class, actual);
        }

        private void parseTillEof(String input) {
            OneCTree tree = (OneCTree) actual.parse(input);
            InternalSyntaxToken lastToken = (InternalSyntaxToken) tree.lastToken();

            if (lastToken == null)  {
                throw new RecognitionException(0, "Did not match till EOF : Last syntax token cannot be found");
            }

            if (!lastToken.isEOF() && (lastToken.column()+lastToken.text().length() != input.length())) {
                throw new RecognitionException(
                        0, "Did not match till EOF, but till line " + lastToken.line() + ": token \"" + lastToken.text() + "\"");
            }
        }

        public ParserAssert matches(String input) {
            isNotNull();
            Preconditions.checkArgument(!hasTrailingWhitespaces(input), "Trailing whitespaces in input are not supported");
            String expected = "Rule '" + getRuleName() + "' should match:\n" + input;
            try {
                parseTillEof(input);
            } catch (RecognitionException e) {  //NOSONAR
                String actual = e.getMessage();
                throw new ParsingResultComparisonFailure(expected, actual);
            }
            return this;
        }

        private static boolean hasTrailingWhitespaces(String input) {
            return input.endsWith(" ") || input.endsWith("\n") || input.endsWith("\r") || input.endsWith("\t");
        }

        public ParserAssert notMatches(String input) {
            isNotNull();
            try {
                parseTillEof(input);
            } catch (RecognitionException e) {  //NOSONAR
                // expected
                return this;
            }
            throw new AssertionError("Rule '" + getRuleName() + "' should not match:\n" + input);
        }

        private String getRuleName() {
            return actual.rootRule().toString();
        }

    }

}
