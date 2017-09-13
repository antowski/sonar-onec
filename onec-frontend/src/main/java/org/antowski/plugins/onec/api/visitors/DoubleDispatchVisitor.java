package org.antowski.plugins.onec.api.visitors;

import com.google.common.base.Preconditions;
import org.antowski.plugins.onec.api.tree.CompilationUnitTree;
import org.antowski.plugins.onec.api.tree.Tree;

import javax.annotation.Nullable;
import java.util.List;

public abstract class DoubleDispatchVisitor implements TreeVisitor {

    private TreeVisitorContext context = null;

    @Override
    public TreeVisitorContext getContext() {
        Preconditions.checkState(context != null, "this#scanTree(context) should be called to initialised the context before accessing it");
        return context;
    }

    @Override
    public final void scanTree(TreeVisitorContext context) {
        this.context = context;
        scan(context.getTopTree());
    }

    protected void scan(@Nullable Tree tree) {
        if (tree != null) {
            tree.accept(this);
        }
    }

    protected <T extends Tree> void scan(List<T> trees) {
        trees.forEach(this::scan);
    }

    public void visitCompilationUnit(CompilationUnitTree tree) {

    }


}
