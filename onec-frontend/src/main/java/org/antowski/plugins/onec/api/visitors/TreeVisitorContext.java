package org.antowski.plugins.onec.api.visitors;

import java.io.File;

import org.antowski.plugins.onec.api.tree.CompilationUnitTree;

public interface TreeVisitorContext {

    /**
     * @return the top tree node of the current file AST representation.
     */
    CompilationUnitTree getTopTree();

    /**
     * @return the current file
     */
    File getFile();

}
