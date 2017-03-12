package org.antowski.sonar.plugins.onec;

import org.junit.Test;
import org.sonar.api.config.MapSettings;
import org.sonar.api.config.Settings;

import static org.assertj.core.api.Assertions.assertThat;

public class OneCTest {

    @Test
    public void should_return_file_suffixes() {

        Settings settings = new MapSettings();
        OneC language = new OneC(settings);

        // default
        assertThat(language.getFileSuffixes()).containsOnly(".1s");

        settings.setProperty(OneCPlugin.FILE_SUFFIXES_KEY, "");
        assertThat(language.getFileSuffixes()).containsOnly(".1s");

        settings.setProperty(OneCPlugin.FILE_SUFFIXES_KEY, ".bar, .foo");
        assertThat(language.getFileSuffixes()).containsOnly(".bar", ".foo");
    }
}
