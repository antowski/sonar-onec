package org.antowski.onec.tree.impl;

import org.antowski.plugins.onec.api.tree.Tree;
import org.antowski.plugins.onec.api.visitors.DoubleDispatchVisitor;

import java.util.Iterator;

public class InternalSyntaxSpacing extends OneCTree {

    private final int start;
    private final int end;

    public InternalSyntaxSpacing(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Kind getKind() {
        return Tree.Kind.TRIVIA;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public Iterator<Tree> childrenIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(DoubleDispatchVisitor visitor) {
        // Do nothing at the moment. Spacings are dropped anyway.
    }

    public int start() {
        return start;
    }

    public int end() {
        return end;
    }
}