package org.antowski.onec.tree.impl;

import com.google.common.collect.Iterables;
import org.antowski.plugins.onec.api.tree.CompilationUnitTree;
import org.antowski.plugins.onec.api.tree.Tree;
import org.antowski.plugins.onec.api.tree.VariableDeclarationTree;
import org.antowski.plugins.onec.api.tree.lexical.SyntaxToken;
import org.antowski.plugins.onec.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CompilationUnitTreeImpl extends OneCTree implements CompilationUnitTree {

    private final List<VariableDeclarationTree> variableDeclarations;
    private final SyntaxToken eofToken;

    public CompilationUnitTreeImpl(@Nullable List<VariableDeclarationTree> variableDeclarations, SyntaxToken eofToken) {
        this.variableDeclarations = variableDeclarations;
        this.eofToken = eofToken;
    }

    @Override
    public Kind kind() {
        return Kind.COMPILATION_UNIT;
    }

    @Nullable
    @Override
    public List<VariableDeclarationTree> variableDeclarations() {
        return variableDeclarations;
    }

    @Override
    public void accept(DoubleDispatchVisitor visitor) {
        visitor.visitCompilationUnit(this);
    }

    @Override
    public Iterable<Tree> children() {
        return Iterables.concat(
                //variableDeclarations,
                Collections.<Tree>singletonList(eofToken));
    }

    @Override
    public SyntaxToken EOFToken() {
        return eofToken;
    }

}
