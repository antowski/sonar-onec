package org.antowski.plugins.onec.api.visitors;

public interface TreeVisitor {

  TreeVisitorContext getContext();

  void scanTree(TreeVisitorContext context);

}
