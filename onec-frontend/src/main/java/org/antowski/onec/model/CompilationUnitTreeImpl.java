package org.antowski.onec.model;

import com.google.common.collect.Iterables;
import org.antowski.plugins.onec.api.tree.CompilationUnitTree;
import org.antowski.plugins.onec.api.tree.Tree;
import org.antowski.plugins.onec.api.tree.lexical.SyntaxToken;
import org.antowski.plugins.onec.api.visitors.DoubleDispatchVisitor;

import java.util.Collections;

public class CompilationUnitTreeImpl extends OneCTree implements CompilationUnitTree {

    private final SyntaxToken eofToken;

    public CompilationUnitTreeImpl(SyntaxToken eofToken) {
        super(Kind.COMPILATION_UNIT);
        this.eofToken = eofToken;
    }

    @Override
    public Kind kind() {
        return Kind.COMPILATION_UNIT;
    }

    @Override
    public void accept(DoubleDispatchVisitor visitor) {
        visitor.visitCompilationUnit(this);
    }

    @Override
    public Iterable<Tree> children() {
        return Iterables.concat(
                Collections.<Tree>singletonList(eofToken));
    }

    @Override
    public SyntaxToken EOFToken() {
        return eofToken;
    }

}
