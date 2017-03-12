package org.antowski.plugins.onec.api.visitors;

import com.google.common.annotations.Beta;

import java.io.File;

@Beta
public interface TreeVisitorContext {

  /**
   * @return the top tree node of the current file AST representation.
   */
  //CompilationUnitTree getTopTree();

  /**
   * @return the current file
   */
  File getFile();

}
