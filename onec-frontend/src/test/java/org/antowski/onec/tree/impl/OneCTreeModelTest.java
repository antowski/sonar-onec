package org.antowski.onec.tree.impl;

import com.sonar.sslr.api.typed.ActionParser;
import org.antowski.onec.parser.OneCParser;
import org.antowski.plugins.onec.api.tree.CompilationUnitTree;
import org.antowski.plugins.onec.api.tree.Tree;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

public class OneCTreeModelTest {

    private final ActionParser<Tree> p = OneCParser.createParser(StandardCharsets.UTF_8);

    @Test
    public void ParseEmptyString() throws Exception {
        CompilationUnitTree empty = compilationUnit("");

        Logger log = Logger.getLogger(OneCTreeModelTest.class.getName());
        OneCTree ot = (OneCTree) empty;
        //log.info(Integer.toString(ot.getLine()));

        //assertThat(((OneCTree) empty).getLine()).isEqualTo(1);
    }

    private CompilationUnitTree compilationUnit(String code) {
        return (CompilationUnitTree) p.parse(code);
    }

}
