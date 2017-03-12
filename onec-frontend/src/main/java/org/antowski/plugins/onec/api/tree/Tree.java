
package org.antowski.plugins.onec.api.tree;

import com.google.common.annotations.Beta;

import org.antowski.plugins.onec.api.tree.lexical.SyntaxToken;
import org.antowski.plugins.onec.api.tree.lexical.SyntaxTrivia;

import org.sonar.sslr.grammar.GrammarRuleKey;

/**
 * Common interface for all nodes in an abstract syntax tree.
 */
@Beta
public interface Tree {

    boolean is(Kinds... kind);

    public enum Kind implements GrammarRuleKey, Kinds {

        TOKEN(SyntaxToken.class),

        TRIVIA(SyntaxTrivia.class);

        final Class<? extends Tree> associatedInterface;

        Kind(Class<? extends Tree> associatedInterface) {
            this.associatedInterface = associatedInterface;
        }

        public Class<? extends Tree> getAssociatedInterface() {
            return associatedInterface;
        }

        @Override
        public boolean contains(Kinds other) {
            return this.equals(other);
        }

    }

}
