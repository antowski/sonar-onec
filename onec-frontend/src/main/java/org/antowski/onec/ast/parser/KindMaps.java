package org.antowski.onec.ast.parser;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.antowski.onec.ast.api.OneCTokenType;
import org.antowski.plugins.onec.api.tree.Tree;
import org.sonar.sslr.grammar.GrammarRuleKey;

import java.util.Map;

public final class KindMaps {

    private final Map<GrammarRuleKey, Tree.Kind> literals;

    public KindMaps() {
        ImmutableMap.Builder<GrammarRuleKey, Tree.Kind> literalsBuilder = ImmutableMap.builder();
        literalsBuilder.put(OneCTokenType.NUMERIC_LITERAL, Tree.Kind.NUMERIC_LITERAL);
        literalsBuilder.put(OneCTokenType.DATE_LITERAL, Tree.Kind.DATE_LITERAL);
        literalsBuilder.put(OneCTokenType.STRING_LITERAL, Tree.Kind.STRING_LITERAL);
        this.literals = literalsBuilder.build();
    }

    public Tree.Kind getLiteral(GrammarRuleKey grammarRuleKey) {
        return Preconditions.checkNotNull(literals.get(grammarRuleKey), "Mapping not found for literal %s", grammarRuleKey);
    }

}
