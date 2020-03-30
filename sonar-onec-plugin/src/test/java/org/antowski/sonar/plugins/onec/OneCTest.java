package org.antowski.sonar.plugins.onec;

import org.junit.Test;
import org.sonar.api.config.internal.MapSettings;

import static org.assertj.core.api.Assertions.assertThat;

public class OneCTest {

    @Test
    public void should_return_file_suffixes() {

        MapSettings config = new MapSettings();
        OneC language = new OneC(config.asConfig());

        // default
        assertThat(language.getFileSuffixes()).containsOnly(".1s");

        config.setProperty(OneCPlugin.FILE_SUFFIXES_KEY, "");
        assertThat(language.getFileSuffixes()).containsOnly(".1s");

        config.setProperty(OneCPlugin.FILE_SUFFIXES_KEY, ".bar, .foo");
        assertThat(language.getFileSuffixes()).containsOnly(".bar", ".foo");
    }
}
