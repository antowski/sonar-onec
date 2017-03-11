/*
 * SonarQube 1C:Enterprise 7.7 Plugin
 * Copyright (C) 2017 antowski
 * mailto:antowski AT gmail DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.antowski.sonar.plugins.onec;

import org.antowski.onec.checks.CheckList;

import org.junit.Test;
import org.mockito.Mockito;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.FileMetadata;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.internal.ActiveRulesBuilder;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.internal.google.common.base.Charsets;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.rule.RuleKey;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OneCSquidSensorTest {

    private final File baseDir = new File("src/test/resources");
    private SensorContextTester context = SensorContextTester.create(baseDir);
    private ActiveRules activeRules;

    /*@Test
    public void testToString() {
        assertThat(sensor().toString()).isEqualTo("OneCSquidSensor");
    }*/

    @Test
    public void testExecute() {
        
        activeRules = (new ActiveRulesBuilder())
            .create(RuleKey.of(CheckList.REPOSITORY_KEY, "PrintStatementUsage"))
            .setName("Print Statement Usage")
            .activate()
            .build();

        inputFile("file1.1s");

        sensor().execute(context);

        String key = "moduleKey:file1.1s";
        assertThat(context.measure(key, CoreMetrics.NCLOC).value()).isEqualTo(8);
        /*assertThat(context.measure(key, CoreMetrics.STATEMENTS).value()).isEqualTo(20);
        assertThat(context.measure(key, CoreMetrics.FUNCTIONS).value()).isEqualTo(4);
        assertThat(context.measure(key, CoreMetrics.CLASSES).value()).isEqualTo(1);
        assertThat(context.measure(key, CoreMetrics.COMPLEXITY).value()).isEqualTo(4);*/
        //assertThat(context.measure(key, CoreMetrics.COMMENT_LINES).value()).isEqualTo(3);

    }

    private OneCSquidSensor sensor() {
        FileLinesContextFactory fileLinesContextFactory = mock(FileLinesContextFactory.class);
        FileLinesContext fileLinesContext = mock(FileLinesContext.class);
        when(fileLinesContextFactory.createFor(Mockito.any(InputFile.class))).thenReturn(fileLinesContext);
        CheckFactory checkFactory = new CheckFactory(activeRules);
        return new OneCSquidSensor(fileLinesContextFactory, checkFactory);
    }

    private InputFile inputFile(String name) {
        DefaultInputFile inputFile = new DefaultInputFile("moduleKey", name)
            .setModuleBaseDir(baseDir.toPath())
            .setType(Type.MAIN)
            .setLanguage(OneC.KEY);
        context.fileSystem().add(inputFile);
        inputFile.initMetadata(new FileMetadata().readMetadata(inputFile.file(), Charsets.UTF_8));
        return inputFile;
    }

}
