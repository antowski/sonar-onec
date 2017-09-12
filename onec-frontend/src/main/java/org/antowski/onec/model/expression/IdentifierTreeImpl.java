package org.antowski.onec.model.expression;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import org.antowski.onec.model.InternalSyntaxToken;
import org.antowski.onec.model.OneCTree;
import org.antowski.plugins.onec.api.tree.Tree;
import org.antowski.plugins.onec.api.tree.expression.IdentifierTree;
import org.antowski.plugins.onec.api.tree.lexical.SyntaxToken;
import org.antowski.plugins.onec.api.visitors.TreeVisitor;

import java.util.Collections;

public class IdentifierTreeImpl extends OneCTree implements IdentifierTree {

    private final SyntaxToken token;

    public IdentifierTreeImpl(SyntaxToken token) {
        super(Kind.IDENTIFIER);
        this.token = Preconditions.checkNotNull(token);
    }

    @Override
    public Kind kind() {
        return Kind.IDENTIFIER;
    }

    @Override
    public SyntaxToken token() {
        return token;
    }

    @Override
    public String name() {
        return token().text();
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visitIdentifier(this);
    }

    @Override
    public String toString() {
        return name();
    }

    @Override
    public Iterable<Tree> children() {
        return Iterables.concat(Collections.singletonList(token));
    }

}