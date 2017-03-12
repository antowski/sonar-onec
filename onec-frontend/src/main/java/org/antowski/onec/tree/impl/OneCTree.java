
package org.antowski.onec.tree.impl;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.antowski.plugins.onec.api.tree.Kinds;
import org.antowski.plugins.onec.api.tree.Tree;
import org.antowski.plugins.onec.api.tree.lexical.SyntaxToken;

public abstract class OneCTree implements Tree {

    private OneCTree parent;

    public int getLine() {
        return getFirstToken().line();
    }

    @Override
    public final boolean is(Kinds... kind) {
        if (getKind() != null) {
            for (Kinds kindIter : kind) {
                if (kindIter.contains(getKind())) {
                    return true;
                }
            }
        }
        return false;
    }

    public abstract Kind getKind();

    /**
     * Creates iterator for children of this node.
     * Note that iterator may contain {@code null} elements.
     *
     * @throws UnsupportedOperationException if {@link #isLeaf()} returns {@code true}
     */
    public abstract Iterator<Tree> childrenIterator();

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

    public boolean isAncestorOf(OneCTree tree) {
        Tree parentTree = tree.getParent();
        if (this.equals(parentTree)) {
            return true;
        }
        if (parentTree == null) {
            return false;
        }
        return this.isAncestorOf((OneCTree) parentTree);
    }

    public Stream<OneCTree> descendants() {
        if (this.isLeaf()) {
            return Stream.empty();
        }
        Stream<OneCTree> kins = childrenStream().filter(Objects::nonNull)
                .filter(tree -> tree instanceof OneCTree)
                .map(tree -> (OneCTree) tree);
        for (Iterator<Tree> iterator = this.childrenIterator(); iterator.hasNext(); ) {
            Tree tree = iterator.next();
            if (tree != null) {
                kins = Stream.concat(kins, ((OneCTree) tree).descendants());
            }
        }
        return kins;
    }

    private Stream<Tree> childrenStream() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(childrenIterator(), Spliterator.ORDERED), false);
    }

    public void setParent(Tree parent) {
        this.parent = (OneCTree) parent;
    }

    public OneCTree getParent() {
        return parent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Tree> children = childrenIterator();
        while (children.hasNext()) {
            sb.append(children.next());
            sb.append(" ");
        }
        return sb.toString();
    }
}
