
package org.antowski.onec;

import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;

public final class OneCParser {

    private OneCParser() {
    }

    public static Parser<Grammar> create(OneCConfiguration conf) {
        return Parser.builder(OneCGrammar.create().build())
                .withLexer(OneCLexer.create(conf)).build();
    }

}
