
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
