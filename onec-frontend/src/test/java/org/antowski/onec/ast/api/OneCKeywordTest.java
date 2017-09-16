package org.antowski.onec.ast.api;

import org.antowski.onec.utils.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OneCKeywordTest {

    @Test
    public void test() {
        assertThat(OneCKeyword.values()).hasSize(25);
        assertThat(OneCKeyword.keywordValues()).hasSize(2 * OneCKeyword.values().length);

        for (OneCKeyword keyword : OneCKeyword.values()) {
            assertThat(keyword.getName()).isEqualTo(keyword.name());
            assertThat(keyword.getEnValue()).isNotNull();
            assertThat(keyword.getRuValue()).isNotNull();
        }
    }

    @Test
    public void test_if() {
        Assertions.assertThat(OneCKeyword.IF)
                .matches("if")
                .matches("IF")
                .matches("iF")
                .matches("If")
                .matches("еСлИ")
                .matches("ЕСЛИ")

                .notMatches("яСЛИ")
                .notMatches("sif")
                .notMatches("if2");
    }

    @Test
    public void test_elseif() {
        Assertions.assertThat(OneCKeyword.ELSIF)
                .matches("elsif")
                .matches("ИначееСлИ")

                .notMatches("elseif");
    }

}