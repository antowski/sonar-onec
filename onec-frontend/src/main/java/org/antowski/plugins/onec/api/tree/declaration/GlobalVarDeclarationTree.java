package org.antowski.plugins.onec.api.tree.declaration;

import org.antowski.plugins.onec.api.tree.Tree;
import org.antowski.plugins.onec.api.tree.lexical.SyntaxToken;

import javax.annotation.Nullable;

/**
 * Объявление глобальной переменной или переменной модуля
 * <pre>
 *   Перем {@link #идетификатор()} [Экспорт];
 * </pre>
 */

public interface GlobalVarDeclarationTree extends Tree {

    SyntaxToken varKeyword();

    @Nullable
    SyntaxToken exportKeyword();

    SyntaxToken semicolonToken();

}
