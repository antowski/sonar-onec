package org.antowski.onec.tree.impl;

/*

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.sonar.javascript.utils.JavaScriptTreeModelTest;
import org.sonar.plugins.javascript.api.tree.Tree;
import org.sonar.plugins.javascript.api.tree.Tree.Kind;
import org.sonar.plugins.javascript.api.tree.expression.IdentifierTree;
import org.sonar.plugins.javascript.api.tree.lexical.SyntaxToken;
import org.sonar.plugins.javascript.api.tree.statement.VariableStatementTree;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OneCTreeTest extends JavaScriptTreeModelTest {

  private OneCTree tree;

  @Before
  public void setUp() {
    tree = createTree();
  }

  @Test
  public void is_leaf() throws Exception {
    assertThat(tree.isLeaf()).isFalse();
  }

  @Test
  public void get_kind() throws Exception {
    assertThat(tree.is(Kind.SCRIPT)).isTrue();
    assertThat(tree.is(Kind.ARRAY_LITERAL)).isFalse();
  }

  @Test
  public void get_first_token() throws Exception {
    SyntaxToken token = tree.getFirstToken();

    assertThat(token).isNotNull();
    assertThat(token.toString()).isEqualTo("first");
  }

  @Test
  public void get_last_token() throws Exception {
    SyntaxToken token = tree.getLastToken();

    assertThat(token).isNotNull();
    assertThat(token.toString()).isEqualTo("last");
  }

  @Test
  public void to_string() throws Exception {
    assertThat(tree.toString()).isEqualTo("child child ");
  }

  @Test
  public void get_line() throws Exception {
    assertThat(tree.getLine()).isEqualTo(1967);
  }

  @Test
  public void no_descendants_in_token() throws Exception {
    OneCTree token = parse("a", Kind.TOKEN);
    assertThat(token.descendants().count()).isEqualTo(0);
  }

  @Test
  public void descendants_include_all_immediate_children() throws Exception {
    VariableStatementTree variable = parse("var a;", Kind.VARIABLE_STATEMENT);
    Set<OneCTree> descendants = ((OneCTree) variable).descendants().collect(Collectors.toSet());
    OneCTree declaration = (OneCTree) variable.declaration();
    OneCTree semicolonToken = (OneCTree) variable.semicolonToken();
    assertThat(descendants).contains(declaration, semicolonToken);
  }

  @Test
  public void descendants_include_children_of_children() throws Exception {
    VariableStatementTree variable = parse("var a;", Kind.VARIABLE_STATEMENT);
    Set<OneCTree> descendants = ((OneCTree) variable).descendants().collect(Collectors.toSet());
    IdentifierTree identifier = (IdentifierTree) variable.declaration().variables().get(0);
    OneCTree declarationToken = (OneCTree) variable.declaration().token();
    OneCTree identifierToken = (OneCTree) identifier.identifierToken();
    assertThat(descendants).contains(declarationToken, (OneCTree) identifier, identifierToken);
  }

  private OneCTree createTree() {
    // usage of Mockito.CALLS_REAL_METHODS allows us to use abstract classes

    TestChildTree child1 = mock(TestChildTree.class, Mockito.CALLS_REAL_METHODS);
    TestChildTree child2 = mock(TestChildTree.class, Mockito.CALLS_REAL_METHODS);

    OneCTree tree = mock(OneCTree.class);
    when(tree.toString()).thenCallRealMethod();
    when(tree.getFirstToken()).thenCallRealMethod();
    when(tree.getLastToken()).thenCallRealMethod();
    when(tree.getLine()).thenCallRealMethod();
    when(tree.getKind()).thenReturn(Kind.SCRIPT);
    when(tree.childrenIterator()).thenReturn(Arrays.asList((Tree) child1, (Tree) child2).iterator());

    return tree;
  }

  /**
   * Test class for the children of a {@link OneCTree}.
   * In this case it is more readable to implement a class than to use all smart features of Mockito.
   */

/*
  private static abstract class TestChildTree extends OneCTree {

    @Override
    public Iterator<Tree> childrenIterator() {
      return new LinkedList<Tree>().iterator();
    }

    @Override
    public SyntaxToken getFirstToken() {
      SyntaxToken token = Mockito.mock(SyntaxToken.class);
      Mockito.when(token.toString()).thenReturn("first");
      Mockito.when(token.line()).thenReturn(1967);
      return token;
    }

    @Override
    public SyntaxToken getLastToken() {
      SyntaxToken token = Mockito.mock(SyntaxToken.class);
      Mockito.when(token.toString()).thenReturn("last");
      return token;
    }

    @Override
    public String toString() {
      return "child";
    }

  }

}
*/