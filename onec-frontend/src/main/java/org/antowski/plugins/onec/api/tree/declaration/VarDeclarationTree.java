package org.antowski.plugins.onec.api.tree.declaration;

import org.antowski.plugins.onec.api.tree.Tree;
import org.antowski.plugins.onec.api.tree.lexical.SyntaxToken;

import javax.annotation.Nullable;

/**
 * Объявление переменной
 * <pre>
 *   Перем {@link #name()} [Экспорт];
 * </pre>
 */

public interface VarDeclarationTree extends Tree {

    SyntaxToken token();

    String name();

}
