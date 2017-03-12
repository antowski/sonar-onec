
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
