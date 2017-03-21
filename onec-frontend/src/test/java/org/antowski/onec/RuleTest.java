package org.antowski.onec;

import com.google.common.base.Charsets;

import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;
import org.antowski.onec.parser.OneCParser;
import org.sonar.sslr.grammar.GrammarRuleKey;

public abstract class RuleTest {

  protected Parser<Grammar> p = OneCParser.create(new OneCConfiguration(Charsets.UTF_8));

  protected void setRootRule(GrammarRuleKey ruleKey) {
    p.setRootRule(p.getGrammar().rule(ruleKey));
  }
}
