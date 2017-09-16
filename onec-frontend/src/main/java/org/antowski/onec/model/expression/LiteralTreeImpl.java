package org.antowski.onec.model.expression;

import com.google.common.base.Preconditions;
import org.antowski.onec.model.InternalSyntaxToken;
import org.antowski.onec.model.OneCTree;
import org.antowski.plugins.onec.api.tree.Tree;
import org.antowski.plugins.onec.api.tree.expression.LiteralTree;
import org.antowski.plugins.onec.api.tree.lexical.SyntaxToken;
import org.antowski.plugins.onec.api.visitors.DoubleDispatchVisitor;

import java.util.Collections;

public class LiteralTreeImpl extends OneCTree implements LiteralTree {

    private final Kind kind;
    private final InternalSyntaxToken token;

    public LiteralTreeImpl(Kind kind, InternalSyntaxToken token) {
        super(kind);
        this.kind = Preconditions.checkNotNull(kind);
        this.token = token;
    }

    @Override
    public Kind kind() {
        return kind;
    }

    @Override
    public String value() {
        return token.text();
    }

    @Override
    public SyntaxToken token() {
        return token;
    }

    @Override
    public void accept(DoubleDispatchVisitor visitor) {
        visitor.visitLiteral(this);
    }

    @Override
    public Iterable<Tree> children() {
        return Collections.<Tree>singletonList(token);
    }

}
