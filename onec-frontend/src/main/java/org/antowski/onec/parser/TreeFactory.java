package org.antowski.onec.parser;

import com.google.common.collect.ImmutableList;
import org.antowski.onec.model.CompilationUnitTreeImpl;
import org.antowski.onec.model.InternalSyntaxToken;
import org.antowski.onec.model.OneCTree;

public class TreeFactory {

    private final KindMaps kindMaps = new KindMaps();

    public CompilationUnitTreeImpl newCompilationUnit(
            OneCTree spacing,
            InternalSyntaxToken eof) {



        return new CompilationUnitTreeImpl(eof);
    }

}
