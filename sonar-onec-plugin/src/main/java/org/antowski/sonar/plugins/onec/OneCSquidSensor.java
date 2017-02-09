/*
 * 1C:Enterprise 7.7 language plugin for SonarQube
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

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.utils.log.Loggers;
import org.sonar.api.utils.log.Logger;

import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;

public class OneCSquidSensor implements Sensor {

    private static final Logger mLogger = Loggers.get(OneCSquidSensor.class);

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor.onlyOnLanguage(OneC.KEY).name("OneCSquidSensor");       
    }

    @Override
    public void execute(SensorContext context) {

        mLogger.info(" ------- OneCSquidSensor works!");

        FileSystem fileSystem = context.fileSystem();


        FilePredicate OneCFilePredicate = fileSystem.predicates().and(
            fileSystem.predicates().hasLanguage(OneC.KEY));
        
        ArrayList<InputFile> inputFiles = Lists.newArrayList(fileSystem.inputFiles(OneCFilePredicate));

        for (InputFile inputFile : inputFiles) {
            analyseFile(context, inputFile);
        }

    }

    private void analyseFile(SensorContext context, InputFile inputFile) {
        mLogger.info("analyse file '" + inputFile.absolutePath() + "'\n"
            + "\t language: " + inputFile.language() + "\n"
            + "\t charset: " + inputFile.charset());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
