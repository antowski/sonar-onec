package org.antowski.onec.parser;

import com.google.common.collect.Lists;
import com.sonar.sslr.api.*;
import com.sonar.sslr.api.typed.Input;
import com.sonar.sslr.api.typed.NodeBuilder;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.antowski.onec.tree.impl.InternalSyntaxSpacing;
import org.antowski.onec.tree.impl.InternalSyntaxToken;
import org.antowski.onec.tree.impl.InternalSyntaxTrivia;
import org.antowski.plugins.onec.api.tree.lexical.SyntaxTrivia;

import java.util.List;

public class OneCNodeBuilder implements NodeBuilder {

    @Override
    public Object createNonTerminal(GrammarRuleKey ruleKey, Rule rule, List<Object> children, int startIndex, int endIndex) {
        for (Object child : children) {
            if (child instanceof InternalSyntaxToken) {
                return child;
            }
        }
        return new InternalSyntaxSpacing(startIndex, endIndex);
    }

    @Override
    public Object createTerminal(Input input, int startIndex, int endIndex, List<Trivia> trivias, TokenType type) {
        boolean isEof = GenericTokenType.EOF.equals(type);
        LineColumnValue lineColumnValue = tokenPosition(input, startIndex, endIndex);
        return new InternalSyntaxToken(lineColumnValue.line, lineColumnValue.column, lineColumnValue.value,
                createTrivias(trivias), startIndex, endIndex, isEof);
    }

    private static List<SyntaxTrivia> createTrivias(List<Trivia> trivias) {
        List<SyntaxTrivia> result = Lists.newArrayList();
        for (Trivia trivia : trivias) {
            Token trivialToken = trivia.getToken();
            result.add(InternalSyntaxTrivia.create(trivialToken.getValue(), trivialToken.getLine(), trivialToken.getColumn()));
        }
        return result;
    }

    private static LineColumnValue tokenPosition(Input input, int startIndex, int endIndex) {
        int[] lineAndColumn = input.lineAndColumnAt(startIndex);
        String value = input.substring(startIndex, endIndex);
        return new LineColumnValue(lineAndColumn[0], lineAndColumn[1] - 1, value);
    }

    private static class LineColumnValue {
        final int line;
        final int column;
        final String value;

        private LineColumnValue(int line, int column, String value) {
            this.line = line;
            this.column = column;
            this.value = value;
        }
    }

}