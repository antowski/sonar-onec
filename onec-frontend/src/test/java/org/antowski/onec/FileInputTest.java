package org.antowski.onec;

import org.junit.Before;
import org.junit.Test;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class FileInputTest extends RuleTest  {

 
    @Before
    public void init() {
        setRootRule(OneCGrammar.FILE_INPUT);
    }

    @Test
    public void test() {
        assertThat(p).matches("any text");
        assertThat(p).matches("");
        assertThat(p).matches("  \n ");
    }

}
