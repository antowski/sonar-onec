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

package org.antowski.onec;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import com.sonar.sslr.api.AstAndTokenVisitor;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.GenericTokenType;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.api.Token;
import com.sonar.sslr.api.Trivia;

import java.util.Map;
import java.util.Set;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;

import org.sonar.squidbridge.SquidAstVisitor;
import org.sonar.squidbridge.api.SourceFile;

public class FileLinesVisitor extends SquidAstVisitor<Grammar> implements AstAndTokenVisitor {

    private Set<Integer> linesOfCode = Sets.newHashSet();
    private Set<Integer> linesOfComments = Sets.newHashSet();

    private final FileSystem fileSystem;
    private final Map<InputFile, Set<Integer>> allLinesOfCode;

    public FileLinesVisitor(FileSystem fileSystem, Map<InputFile, Set<Integer>> linesOfCode) {
        this.fileSystem = fileSystem;
        this.allLinesOfCode = linesOfCode;    
    }

     @Override
    public void visitFile(AstNode astNode) {
        linesOfCode.clear();
        linesOfComments.clear();
    }

    @Override
    public void visitToken(Token token) {

        if (token.getType().equals(GenericTokenType.EOF)) {
            return;
        }
        
        if(token.getType() == GenericTokenType.UNKNOWN_CHAR){
            linesOfCode.add(token.getLine());
        }

        if(token.getType() == GenericTokenType.COMMENT){
            linesOfComments.add(token.getLine());
        }
    }

    @Override
    public void leaveFile(AstNode astNode) {
        
        InputFile inputFile = fileSystem.inputFile(fileSystem.predicates().is(getContext().getFile()));
        if (inputFile == null){
            throw new IllegalStateException("InputFile is null, but it should not be.");
        }
        
        allLinesOfCode.put(inputFile, ImmutableSet.copyOf(linesOfCode));
        
        getContext().peekSourceCode().add(OneCMetric.LINES_OF_CODE, linesOfCode.size());
        getContext().peekSourceCode().add(OneCMetric.COMMENT_LINES, linesOfComments.size());

    }

}
