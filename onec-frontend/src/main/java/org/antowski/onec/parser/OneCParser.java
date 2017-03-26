
package org.antowski.onec.parser;

import com.sonar.sslr.api.typed.ActionParser;
import org.antowski.onec.model.OneCTree;
import org.antowski.plugins.onec.api.tree.Tree;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import java.io.File;
import java.nio.charset.Charset;

public class OneCParser extends ActionParser<Tree> {

    private OneCParser(Charset charset, LexerlessGrammarBuilder grammarBuilder, Class<OneCGrammar> onecGrammarClass,
                       TreeFactory treeFactory, OneCNodeBuilder onecNodeBuilder, OneCLexer compilationUnit) {
        super(charset, grammarBuilder, onecGrammarClass, treeFactory, onecNodeBuilder, compilationUnit);
    }

    public static ActionParser<Tree> createParser(Charset charset) {
        return new OneCParser(
                charset,
                OneCLexer.createGrammarBuilder(),
                OneCGrammar.class,
                new TreeFactory(),
                new OneCNodeBuilder(),
                OneCLexer.COMPILATION_UNIT);
    }

    @Override
    public Tree parse(File file) {
        return createParentLink((OneCTree) super.parse(file));
    }

    @Override
    public Tree parse(String source) {
        return createParentLink((OneCTree) super.parse(source));
    }

    private static Tree createParentLink(OneCTree parent) {
        if (!parent.isLeaf()) {
            for (Tree nextTree : parent.getChildren()) {
                OneCTree next = (OneCTree) nextTree;
                if (next != null) {
                    next.setParent(parent);
                    createParentLink(next);
                }
            }
        }
        return parent;
    }
}
