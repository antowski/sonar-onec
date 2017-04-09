package org.antowski.onec.ast.api;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OneCPunctuatorTest {

    @Test
    public void test() {
        assertThat(OneCPunctuator.values()).hasSize(22);

        for (OneCPunctuator punctuator : OneCPunctuator.values()) {
            assertThat(punctuator.getName()).isEqualTo(punctuator.name());
            assertThat(punctuator.getValue()).isNotNull();
        }
    }

}