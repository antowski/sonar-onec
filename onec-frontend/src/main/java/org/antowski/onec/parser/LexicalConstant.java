package org.antowski.onec.parser;

public class LexicalConstant {

    /**
     * Tab, Vertical Tab, Form Feed, Space, No-break space, Byte Order Mark, Any other Unicode "space separator"
     */
    public static final String WHITESPACE = "\\t\\u000B\\f\\u0020\\u00A0\\uFEFF\\p{Zs}";

    /**
     * IDENTIFIER
     */

    private static final String IDENTIFIER_START = "[_a-zA-Zа-яА-ЯёЁ]";
    public static final String IDENTIFIER_PART = "[" + IDENTIFIER_START + "[0-9]]";
    public static final String IDENTIFIER = IDENTIFIER_START + IDENTIFIER_PART + "*";

    public static final String DATE_LITERAL = "'[^'\\n]*'";
    public static final String NUMERIC_LITERAL = "\\d+\\.*[\\d]*";
    public static final String STRING_LITERAL = "\"[^\"]*\"";

}
