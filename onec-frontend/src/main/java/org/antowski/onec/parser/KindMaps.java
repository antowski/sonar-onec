package org.antowski.onec.parser;

import com.google.common.collect.ImmutableMap;
import org.antowski.plugins.onec.api.tree.Tree;
import org.sonar.sslr.grammar.GrammarRuleKey;

import java.util.Map;

public final class KindMaps {

    private final Map<GrammarRuleKey, Tree.Kind> literals;

    public KindMaps() {
        ImmutableMap.Builder<GrammarRuleKey, Tree.Kind> literalsBuilder = ImmutableMap.builder();
        this.literals = literalsBuilder.build();
    }

}
