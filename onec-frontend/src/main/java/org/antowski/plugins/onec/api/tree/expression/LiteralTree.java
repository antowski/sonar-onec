package org.antowski.plugins.onec.api.tree.expression;

import org.antowski.plugins.onec.api.tree.lexical.SyntaxToken;

/**
 * Literal
 *
 * <pre>
 *   {@link Kind#DATE_LITERAL '01.01.2017'}       // date
 *   {@link Kind#NUMERIC_LITERAL 1.2}             // numeric
 *   {@link Kind#STRING_LITERAL "regular string"} // string without embedded variable
 * </pre>
 */
public interface LiteralTree extends ExpressionTree {

    SyntaxToken token();

    String value();

}