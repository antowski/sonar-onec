
package org.antowski.onec;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.antowski.plugins.onec.api.tree.Tree;
import org.antowski.plugins.onec.api.tree.lexical.SyntaxToken;
import org.antowski.plugins.onec.api.visitors.SubscriptionVisitor;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.measures.FileLinesContextFactory;

public class FileLinesVisitor extends SubscriptionVisitor {

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
    public void visitNode(Tree tree) {
        linesOfCode.clear();
        linesOfComments.clear();
    }

    @Override
    public void visitToken(SyntaxToken syntaxToken) {
        linesOfCode.add(syntaxToken.line());
    }

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return null;
    }
}
