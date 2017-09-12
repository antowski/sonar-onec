package org.antowski.onec.model.declaration;

import org.antowski.onec.model.OneCTree;
import org.antowski.plugins.onec.api.tree.declaration.VarDeclarationTree;
import org.antowski.plugins.onec.api.tree.expression.IdentifierTree;
import org.antowski.plugins.onec.api.tree.lexical.SyntaxToken;

public class VarDeclarationTreeImpl extends OneCTree implements VarDeclarationTree {

    private final SyntaxToken token;
    private final Kind KIND;

    public VarDeclarationTreeImpl(SyntaxToken token) {
        this.token = token;
    }

    @Override
    public Kind getKind() {
        return KIND;
    }

}
