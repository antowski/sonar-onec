package org.antowski.onec.model;

import org.antowski.plugins.onec.api.tree.Tree;
import org.antowski.plugins.onec.api.tree.lexical.SyntaxTrivia;
import org.antowski.plugins.onec.api.visitors.DoubleDispatchVisitor;

import java.util.Collections;
import java.util.List;

public class InternalSyntaxTrivia extends OneCTree implements SyntaxTrivia {

    private final String comment;
    private final int column;
    private int startLine;

    public InternalSyntaxTrivia(String comment, int startLine, int column) {
        this.comment = comment;
        this.startLine = startLine;
        this.column = column;
    }

    @Override
    public String text() {
        return comment;
    }

    @Override
    public List<SyntaxTrivia> trivias() {
        return Collections.emptyList();
    }

    @Override
    public int line() {
        return startLine;
    }

    @Override
    public int column() {
        return column;
    }

    @Override
    public Kind kind() {
        return Kind.TRIVIA;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public Iterable<Tree> children() {
        throw new UnsupportedOperationException();
    }

    public static SyntaxTrivia create(String comment, int startLine, int column) {
        return new InternalSyntaxTrivia(comment, startLine, column);
    }

    @Override
    public int getLine() {
        return startLine;
    }

    @Override
    public void accept(DoubleDispatchVisitor visitor) {
        //FIXME do nothing
    }

}
