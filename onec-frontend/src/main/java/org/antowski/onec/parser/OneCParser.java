
package org.antowski.onec.parser;

import com.google.common.collect.Lists;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.api.typed.ActionParser;
import com.sonar.sslr.impl.Parser;
import org.antowski.onec.OneCConfiguration;
import org.antowski.onec.OneCGrammar;
import org.antowski.onec.OneCLexer;
import org.antowski.onec.tree.impl.OneCTree;
import org.antowski.plugins.onec.api.tree.Tree;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Objects;

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
        return createParentLink(super.parse(file));
    }

    @Override
    public Tree parse(String source) {
        return createParentLink(super.parse(source));
    }

    private static Tree createParentLink(Tree parent) {
        OneCTree onecTree = (OneCTree) parent;
        Iterator<Tree> childrenIterator = onecTree.childrenIterator();
        while (childrenIterator.hasNext()) {
            OneCTree child = (OneCTree) childrenIterator.next();
            if (child != null) {
                child.setParent(parent);
                if (!child.isLeaf()) {
                    createParentLink(child);
                }
            }
        }
        return parent;
    }
}
