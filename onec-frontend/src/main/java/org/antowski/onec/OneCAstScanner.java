
package org.antowski.onec;

import com.google.common.base.Charsets;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;
import java.io.File;
import java.util.Collection;

import org.antowski.onec.parser.OneCParser;
import org.sonar.squidbridge.AstScanner;
import org.sonar.squidbridge.CommentAnalyser;
import org.sonar.squidbridge.SquidAstVisitor;
import org.sonar.squidbridge.SquidAstVisitorContextImpl;
import org.sonar.squidbridge.api.SourceCode;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.api.SourceProject;
import org.sonar.squidbridge.indexer.QueryByType;

public final class OneCAstScanner {

    private OneCAstScanner() {
    }

    /**
     * Helper method for testing checks without having to deploy them on a Sonar instance.
     */
    @SuppressWarnings("unchecked")
    public static SourceFile scanSingleFile(String path, SquidAstVisitor<Grammar>... visitors) {
        File file = new File(path);
        if (!file.isFile()) {
            throw new IllegalArgumentException("File '" + file + "' not found.");
        }
        AstScanner<Grammar> scanner = create(new OneCConfiguration(Charsets.UTF_8), visitors);
        scanner.scanFile(file);
        Collection<SourceCode> sources = scanner.getIndex().search(new QueryByType(SourceFile.class));
        if (sources.size() != 1) {
            throw new IllegalStateException("Only one SourceFile was expected whereas " + sources.size() + " has been returned.");
        }
        return (SourceFile) sources.iterator().next();
    }

    public static AstScanner<Grammar> create(OneCConfiguration conf, SquidAstVisitor<Grammar>... visitors) {
        final SquidAstVisitorContextImpl<Grammar> context = new SquidAstVisitorContextImpl<>(new SourceProject("1C 7.7 project"));
        final Parser<Grammar> parser = OneCParser.create(conf);

        AstScanner.Builder<Grammar> builder = AstScanner.<Grammar>builder(context).setBaseParser(parser);

        builder.withMetrics(OneCMetric.values());

        builder.setFilesMetric(OneCMetric.FILES);

        setCommentAnalyser(builder);

        /*
        setClassesAnalyser(builder);

        setMethodAnalyser(builder);

        setMetrics(builder);
        */

        /* External visitors (typically Check ones) */
        for (SquidAstVisitor<Grammar> visitor : visitors) {
            builder.withSquidAstVisitor(visitor);
        }

        return builder.build();
    }

    private static void setCommentAnalyser(AstScanner.Builder<Grammar> builder) {
        builder.setCommentAnalyser(
            new CommentAnalyser() {
                @Override
                public boolean isBlank(String line) {
                    for (int i = 0; i < line.length(); i++) {
                        if (Character.isLetterOrDigit(line.charAt(i))) {
                            return false;
                        }
                    }
                    return true;
                }

                @Override
                public String getContents(String comment) {
                    // Comment always starts with "//"
                    return comment.substring(comment.indexOf("//"));
                }
        });
  }

}
