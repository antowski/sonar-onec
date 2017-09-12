package org.antowski.onec.parser;

import com.google.common.collect.ImmutableList;
import org.antowski.onec.model.CompilationUnitTreeImpl;
import org.antowski.onec.model.InternalSyntaxToken;
import org.antowski.onec.model.OneCTree;
//import org.antowski.plugins.onec.api.tree.VariableDeclarationTree;

public class TreeFactory {

    private final KindMaps kindMaps = new KindMaps();

    public CompilationUnitTreeImpl newCompilationUnit(
            OneCTree spacing,
            //Optional<List<VariableDeclarationTree>> variableDeclarations,
            InternalSyntaxToken eof) {

//        ImmutableList.Builder<VariableDeclarationTree> variables = ImmutableList.builder();
//        if (variableDeclarations.isPresent()) {
//            for (VariableDeclarationTree child : variableDeclarations.get()) {
//                variables.add(child);
//            }
//        }

        return new CompilationUnitTreeImpl(
                //variables.build(),
                null,
                eof);
    }

}
