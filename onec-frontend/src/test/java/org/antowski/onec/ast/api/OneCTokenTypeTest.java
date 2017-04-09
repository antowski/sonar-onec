package org.antowski.onec.ast.api;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OneCTokenTypeTest {

    @Test
    public void test() {
        assertThat(OneCTokenType.values()).hasSize(4);

        for (OneCTokenType tokenType : OneCTokenType.values()) {
            assertThat(tokenType.getName()).isEqualTo(tokenType.name());
            assertThat(tokenType.getValue()).isNotNull();
        }
    }

}
