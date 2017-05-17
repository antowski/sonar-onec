package org.antowski.plugins.onec.api.visitors;

import org.antowski.plugins.onec.api.tree.expression.IdentifierTree;

public interface TreeVisitor {

  TreeVisitorContext getContext();

  void scanTree(TreeVisitorContext context);

  void visitIdentifier(IdentifierTree tree);
}
