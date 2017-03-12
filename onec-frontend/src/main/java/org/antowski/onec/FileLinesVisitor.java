
package org.antowski.onec;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import com.sonar.sslr.api.AstAndTokenVisitor;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.GenericTokenType;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.api.Token;
import com.sonar.sslr.api.Trivia;

import java.util.Map;
import java.util.Set;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;

import org.sonar.squidbridge.SquidAstVisitor;
import org.sonar.squidbridge.api.SourceFile;

public class FileLinesVisitor extends SquidAstVisitor<Grammar> implements AstAndTokenVisitor {

    private final FileLinesContextFactory fileLinesContextFactory;

    private Set<Integer> linesOfCode = Sets.newHashSet();
    private Set<Integer> linesOfComments = Sets.newHashSet();

    private final FileSystem fileSystem;
    private final Map<InputFile, Set<Integer>> allLinesOfCode;

    public FileLinesVisitor(
        FileLinesContextFactory fileLinesContextFactory, 
        FileSystem fileSystem, 
        Map<InputFile, Set<Integer>> linesOfCode) {
        this.fileLinesContextFactory = fileLinesContextFactory;
        this.fileSystem = fileSystem;
        this.allLinesOfCode = linesOfCode;    
    }

     @Override
    public void visitFile(AstNode astNode) {
        linesOfCode.clear();
        linesOfComments.clear();
    }

    @Override
    public void visitToken(Token token) {

        if (token.getType().equals(GenericTokenType.EOF)) {
            return;
        }
        
        if(token.getType() == GenericTokenType.UNKNOWN_CHAR){
            linesOfCode.add(token.getLine());
        }

        if(token.getType() == GenericTokenType.COMMENT){
            linesOfComments.add(token.getLine());
        }
    }

    @Override
    public void leaveFile(AstNode astNode) {
        
        InputFile inputFile = fileSystem.inputFile(fileSystem.predicates().is(getContext().getFile()));
        if (inputFile == null){
            throw new IllegalStateException("InputFile is null, but it should not be.");
        }
        
        allLinesOfCode.put(inputFile, ImmutableSet.copyOf(linesOfCode));
        
        getContext().peekSourceCode().add(OneCMetric.LINES_OF_CODE, linesOfCode.size());
        getContext().peekSourceCode().add(OneCMetric.COMMENT_LINES, linesOfComments.size());

    }

}
