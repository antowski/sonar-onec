package org.antowski.onec.parser;

import com.google.common.collect.ImmutableList;
import com.sonar.sslr.api.typed.Optional;
import org.antowski.onec.tree.impl.CompilationUnitTreeImpl;
import org.antowski.onec.tree.impl.InternalSyntaxToken;
import org.antowski.onec.tree.impl.OneCTree;
import org.antowski.plugins.onec.api.tree.VariableDeclarationTree;

import java.util.List;

public class TreeFactory {

    private final KindMaps kindMaps = new KindMaps();

    public CompilationUnitTreeImpl newCompilationUnit(
            OneCTree spacing,
            //Optional<List<VariableDeclarationTree>> variableDeclarations,
            InternalSyntaxToken eof) {

        ImmutableList.Builder<VariableDeclarationTree> variables = ImmutableList.builder();
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
