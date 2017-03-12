package org.antowski.plugins.onec.api.tree.lexical;

import com.google.common.annotations.Beta;
import org.antowski.plugins.onec.api.tree.Tree;

import java.util.List;

/**
 * Represents a token in the syntax tree.
 */
@Beta
public interface SyntaxToken extends Tree {

    String text();

    List<SyntaxTrivia> trivias();

    int line();

    int column();

    int endLine();

    int endColumn();
}
