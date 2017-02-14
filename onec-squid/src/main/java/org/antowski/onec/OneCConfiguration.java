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
package org.antowski.onec;

import java.nio.charset.Charset;
import org.sonar.squidbridge.api.SquidConfiguration;

public class OneCConfiguration extends SquidConfiguration {

    private boolean ignoreHeaderComments;

    public OneCConfiguration(Charset charset) {
        super(charset);
    }

    public void setIgnoreHeaderComments(boolean ignoreHeaderComments) {
        this.ignoreHeaderComments = ignoreHeaderComments;
    }

    public boolean getIgnoreHeaderComments() {
        return ignoreHeaderComments;
    }

}
