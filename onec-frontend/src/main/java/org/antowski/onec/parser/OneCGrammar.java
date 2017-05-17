
package org.antowski.onec.parser;

import com.sonar.sslr.api.typed.GrammarBuilder;
import org.antowski.onec.ast.api.OneCKeyword;
import org.antowski.onec.ast.api.OneCPunctuator;
import org.antowski.onec.ast.api.OneCTokenType;
import org.antowski.onec.model.CompilationUnitTreeImpl;
import org.antowski.onec.model.InternalSyntaxToken;
import org.antowski.plugins.onec.api.tree.VariableDeclarationTree;

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

    public GlobalVarDeclarationTreeImpl VARIABLE_DECLARATION() {
        return b.<GlobalVarDeclarationTreeImpl>nonterminal(OneCLexer.GLOBAL_VARIABLE_DECLARATION)
                .is(
                        f.VariableDeclaration(
                                b.token(OneCKeyword.VAR),
                                b.token(OneCTokenType.IDENTIFIER)),
                                b.optional(OneCKeyword.EXPORT),
                                b.token(OneCPunctuator.SEMICOLON));
    }
}
