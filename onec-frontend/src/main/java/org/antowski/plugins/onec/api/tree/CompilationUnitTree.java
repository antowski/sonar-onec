
package org.antowski.plugins.onec.api.tree;

import com.google.common.annotations.Beta;

import javax.annotation.Nullable;

import java.util.List;

import org.antowski.plugins.onec.api.tree.lexical.SyntaxToken;

/**
 * Compilation unit.
 */
@Beta
public interface CompilationUnitTree extends Tree {

    SyntaxToken EOFToken();

}
