package org.antowski.plugins.onec.api.tree.expression;

import org.antowski.plugins.onec.api.tree.lexical.SyntaxToken;

/**
 * Identifier expression.
 */
public interface IdentifierTree extends ExpressionTree {

    SyntaxToken token();

    String name();

}
