
package org.antowski.onec.parser;

import com.sonar.sslr.api.typed.GrammarBuilder;
import org.antowski.onec.model.CompilationUnitTreeImpl;
import org.antowski.onec.model.InternalSyntaxToken;

public class OneCGrammar {

    private final GrammarBuilder<InternalSyntaxToken> b;
    private final TreeFactory f;

    public OneCGrammar(GrammarBuilder<InternalSyntaxToken> b, TreeFactory f) {
        this.b = b;
        this.f = f;
    }

    // Compilation unit

    public CompilationUnitTreeImpl COMPILATION_UNIT() {
        return b.<CompilationUnitTreeImpl>nonterminal(OneCLexer.COMPILATION_UNIT)
                .is(
                        f.newCompilationUnit(
                                b.token(OneCLexer.SPACING),
                                b.token(OneCLexer.EOF)));
    }
}
