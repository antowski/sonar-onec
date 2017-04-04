package org.antowski.onec.ast.api;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OneCKeywordTest {

    @Test
    public void test() {
        assertThat(OneCKeyword.values()).hasSize(2);
        assertThat(OneCKeyword.keywordValues()).hasSize(2 * OneCKeyword.values().length);

        for (OneCKeyword keyword : OneCKeyword.values()) {
            assertThat(keyword.getName()).isEqualTo(keyword.name());
            assertThat(keyword.getEnValue()).isNotNull();
            assertThat(keyword.getRuValue()).isNotNull();
        }
    }

}