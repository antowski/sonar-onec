
package org.antowski.onec.tree.impl;

import java.util.Iterator;

import org.antowski.plugins.onec.api.tree.Kinds;
import org.antowski.plugins.onec.api.tree.Tree;
import org.antowski.plugins.onec.api.tree.lexical.SyntaxToken;

public abstract class OneCTree implements Tree {

    private Tree parent;

    @Override
    public final boolean is(Kind... kind) {
        if (getKind() != null) {
            for (Kind kindIter : kind) {
                if (getKind() == kindIter) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Tree parent() {
        return parent;
    }

    @Override
    public void setParent(Tree parent) {
        this.parent = parent;
    }

    public int getLine() {
        return getFirstToken().line();
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    public SyntaxToken getLastToken() {
        SyntaxToken lastToken = null;
        Iterator<Tree> childrenIterator = childrenIterator();
        while (childrenIterator.hasNext()) {
            OneCTree child = (OneCTree) childrenIterator.next();
            if (child != null) {
                SyntaxToken childLastToken = child.getLastToken();
                if (childLastToken != null) {
                    lastToken = childLastToken;
                }
            }
        }
        return lastToken;
    }

    public SyntaxToken getFirstToken() {
        Iterator<Tree> childrenIterator = childrenIterator();
        Tree child;
        do {
            if (childrenIterator.hasNext()) {
                child = childrenIterator.next();
            } else {
                throw new IllegalStateException("Tree has no non-null children " + getKind());
            }
        } while (child == null);
        return ((OneCTree) child).getFirstToken();
    }
}
