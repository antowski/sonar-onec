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

import com.sonar.sslr.impl.Lexer;
import com.sonar.sslr.impl.channel.BlackHoleChannel;
//import com.sonar.sslr.impl.channel.IdentifierAndKeywordChannel;
//import com.sonar.sslr.impl.channel.PunctuatorChannel;
import com.sonar.sslr.impl.channel.UnknownCharacterChannel;

//import static com.sonar.sslr.api.GenericTokenType.COMMENT;

import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.commentRegexp;
//import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.regexp;

public final class OneCLexer {

  private OneCLexer() {
  }

  //public static Lexer create(PythonConfiguration conf) {
  public static Lexer create(OneCConfiguration conf) {
    
    return Lexer.builder()
        .withCharset(conf.getCharset())
        .withFailIfNoChannelToConsumeOneCharacter(true)

        .withChannel(new BlackHoleChannel("\\s"))

        //.withChannel(regexp(COMMENT, "//[^\\n\\r]*+"))

        .withChannel(commentRegexp("//[^\\n\\r]*+"))

        .withChannel(new UnknownCharacterChannel())

        .build();
  }
}
