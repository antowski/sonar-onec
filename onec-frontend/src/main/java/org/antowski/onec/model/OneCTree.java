
package org.antowski.onec.model;

import java.util.ArrayList;
import java.util.List;

import org.antowski.plugins.onec.api.tree.Tree;
import org.antowski.plugins.onec.api.tree.lexical.SyntaxToken;

import javax.annotation.Nullable;

public abstract class OneCTree implements Tree {

    private Tree parent;

    private List<Tree> children;

    @Override
    @Nullable
    public SyntaxToken firstToken() {
        for (Tree child : getChildren()) {
            SyntaxToken first = child.firstToken();
            if (first != null) {
                return first;
            }
        }
        return null;
    }

    @Override
    @Nullable
    public SyntaxToken lastToken() {
        List<Tree> trees = getChildren();
        for (int index = trees.size() - 1; index >= 0; index--) {
            SyntaxToken last = trees.get(index).lastToken();
            if (last != null) {
                return last;
            }
        }
        return null;
    }

    public int getLine() {
        SyntaxToken firstSyntaxToken = firstToken();
        if (firstSyntaxToken == null) {
            return -1;
        }
        return firstSyntaxToken.line();
    }

    @Override
    public final boolean is(Kind... kind) {
        if (kind() != null) {
            for (Kind kindIter : kind) {
                if (kind() == kindIter) {
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

    /**
     * Creates iterable for children of this node.
     * Note that iterable may contain {@code null} elements.
     *
     * @throws java.lang.UnsupportedOperationException if {@link #isLeaf()} returns {@code true}
     */
    protected abstract Iterable<Tree> children();

    public List<Tree> getChildren() {
        if (children == null) {
            children = new ArrayList<>();
            children().forEach(child -> {
                // null children are ignored
                if (child != null) {
                    children.add(child);
                }
            });
        }
        return children;
    }

    public boolean isLeaf() {
        return false;
    }

}
