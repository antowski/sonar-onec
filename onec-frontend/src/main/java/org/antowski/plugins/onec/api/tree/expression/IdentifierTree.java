package org.antowski.plugins.onec.api.tree.expression;

import org.antowski.plugins.onec.api.tree.lexical.SyntaxToken;

/**
 * Common interface to represent all kinds of identifiers.
 */
public interface IdentifierTree extends ExpressionTree {

    SyntaxToken token();

    String name();

}
