package org.antowski.onec.ast.parser;

import com.google.common.collect.ImmutableList;
import org.antowski.onec.model.CompilationUnitTreeImpl;
import org.antowski.onec.model.InternalSyntaxToken;
import org.antowski.onec.model.OneCTree;
import org.antowski.onec.model.expression.LiteralTreeImpl;
import org.antowski.plugins.onec.api.tree.Tree;
import org.antowski.plugins.onec.api.tree.VariableDeclarationTree;
import org.antowski.plugins.onec.api.tree.expression.ExpressionTree;
import org.antowski.plugins.onec.api.tree.expression.LiteralTree;

public class TreeFactory {

    private final KindMaps kindMaps = new KindMaps();

    public ExpressionTree literal(InternalSyntaxToken token) {
        return new LiteralTreeImpl(kindMaps.getLiteral(token.getGrammarRuleKey()), token);
    }

    public CompilationUnitTreeImpl newCompilationUnit(
            OneCTree spacing,
            //Optional<List<VariableDeclarationTree>> variableDeclarations,
            InternalSyntaxToken eof) {

        ImmutableList.Builder<VariableDeclarationTree> variables = ImmutableList.builder();
//        if (variableDeclarations.isPresent()) {
//            for (VariableDeclarationTree child : variableDeclarations.get()) {
//                variables.add(child);
//            }
//        }

        return new CompilationUnitTreeImpl(
                //variables.build(),
                null,
                eof);
    }

}
