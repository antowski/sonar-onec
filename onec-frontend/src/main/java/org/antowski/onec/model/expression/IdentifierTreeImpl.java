package org.antowski.onec.model.expression;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.antowski.onec.model.InternalSyntaxToken;
import org.antowski.onec.model.OneCTree;
import org.antowski.plugins.onec.api.tree.Tree;
import org.antowski.plugins.onec.api.tree.expression.IdentifierTree;
import org.antowski.plugins.onec.api.tree.lexical.SyntaxToken;
import org.antowski.plugins.onec.api.visitors.TreeVisitor;

import java.util.Collections;
import java.util.List;

public class IdentifierTreeImpl extends OneCTree implements IdentifierTree {

    private final InternalSyntaxToken nameToken;

    public IdentifierTreeImpl(InternalSyntaxToken nameToken) {
        super(Kind.IDENTIFIER);
        this.nameToken = Preconditions.checkNotNull(nameToken);
    }

    @Override
    public Kind kind() {
        return Kind.IDENTIFIER;
    }

    @Override
    public SyntaxToken token() {
        return nameToken;
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
        return Iterables.concat(Collections.singletonList(nameToken));
    }

}