package org.antowski.onec.parser;

import com.sonar.sslr.api.typed.ActionParser;
import org.antowski.plugins.onec.api.tree.CompilationUnitTree;
import org.antowski.plugins.onec.api.tree.Tree;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class OneCParserTest {

    private final ActionParser<Tree> p = OneCParser.createParser(StandardCharsets.UTF_8);

    @Test
    public void test() {

        CompilationUnitTree cut = compilationUnit("");
        assertThat(cut).isNotNull();

    }

    @Ignore
    @Test
    public void testKeyword() {

        CompilationUnitTree cut = compilationUnit("Перем");
        assertThat(cut).isNotNull();

    }

    private CompilationUnitTree compilationUnit(String code) {
        return (CompilationUnitTree) p.parse(code);
    }

}