
package org.antowski.plugins.onec.api.tree;

import com.google.common.annotations.Beta;

import org.antowski.plugins.onec.api.tree.expression.ExpressionTree;
import org.antowski.plugins.onec.api.tree.expression.LiteralTree;
import org.antowski.plugins.onec.api.tree.lexical.SyntaxToken;
import org.antowski.plugins.onec.api.tree.lexical.SyntaxTrivia;

import org.antowski.plugins.onec.api.visitors.DoubleDispatchVisitor;
import org.sonar.sslr.grammar.GrammarRuleKey;

import javax.annotation.Nullable;
import java.util.Iterator;

/**
 * Common interface for all nodes in an abstract syntax tree.
 */
@Beta
public interface Tree {

    boolean is(Kind... kind);

    void accept(DoubleDispatchVisitor visitor);

    Kind kind();

    @Nullable
    Tree parent();

    @Nullable
    SyntaxToken firstToken();

    @Nullable
    SyntaxToken lastToken();

    void setParent(Tree parent);

    public enum Kind implements GrammarRuleKey {

        COMPILATION_UNIT(CompilationUnitTree.class),
        TOKEN(SyntaxToken.class),
        TRIVIA(SyntaxTrivia.class),

        /**
         * {@link LiteralTree}
         * {@code numeric}
         */
        NUMERIC_LITERAL(LiteralTree.class),

        /**
         * {@link LiteralTree}
         * {@code numeric}
         */
        DATE_LITERAL(LiteralTree.class),

        /**
         * {@link LiteralTree}
         * {@code numeric}
         */
        STRING_LITERAL(LiteralTree.class)

        ;

        final Class<? extends Tree> associatedInterface;

        Kind(Class<? extends Tree> associatedInterface) {
            this.associatedInterface = associatedInterface;
        }

        public Class<? extends Tree> getAssociatedInterface() {
            return associatedInterface;
        }

    }

}
