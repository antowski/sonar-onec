
package org.antowski.onec.ast.parser;

import com.sonar.sslr.api.typed.GrammarBuilder;
import org.antowski.onec.ast.api.OneCTokenType;
import org.antowski.onec.model.CompilationUnitTreeImpl;
import org.antowski.onec.model.InternalSyntaxToken;
import org.antowski.plugins.onec.api.tree.expression.ExpressionTree;
import org.antowski.plugins.onec.api.tree.expression.LiteralTree;

public class OneCGrammar {

    private final GrammarBuilder<InternalSyntaxToken> b;
    private final TreeFactory f;

    public OneCGrammar(GrammarBuilder<InternalSyntaxToken> b, TreeFactory f) {
        this.b = b;
        this.f = f;
    }

    // Literals

    public ExpressionTree LITERAL() {
        return b.<ExpressionTree>nonterminal(OneCLexer.LITERAL)
                .is(
                        f.literal(
                                b.firstOf(
                                        b.token(OneCTokenType.DATE_LITERAL),
                                        b.token(OneCTokenType.NUMERIC_LITERAL),
                                        b.token(OneCTokenType.STRING_LITERAL))));
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
