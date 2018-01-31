package asteroid;

import java.util.List;

import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.WhileStatement;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.AssertStatement;
import org.codehaus.groovy.ast.stmt.DoWhileStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.builder.AstBuilder;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import asteroid.statements.TryCatchStatementBuilder;

/**
 * This class hides the different implementations to create expressions through the Groovy api to provide a unified
 * an easier way to create statements when coding an AST transformation.
 *
 * @since 0.1.0
 */
@SuppressWarnings("PMD.UseUtilityClass")
public final class Statements {

    /**
     * Returns an instance of {@link ReturnStatement}
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>returnS(constX("1"))</code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>return "1"</code></pre>
     *
     * @param expression
     * @return an instance of {@link ReturnStatement}
     * @since 0.1.0
     */
    public static ReturnStatement returnS(final Expression expression) {
        return (ReturnStatement) GeneralUtils.returnS(expression);
    }

    /**
     * Returns an instance of {@link Statement}. When declaring a {@link org.codehaus.groovy.ast.MethodNode}
     * the body is always whether a {@link ReturnStatement} if it returns something or just a {@link Statement}
     * if it's void.
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>
     * // body of a void method
     * stmt(callThisX("println", constX("1")));
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>println "1"</code></pre>
     *
     * @param expression
     * @return an instance of {@link Statement}
     * @since 0.1.0
     */
    public static Statement stmt(final Expression expression) {
        return GeneralUtils.stmt(expression);
    }

    /**
     * Returns an  instance of {@link Statement}. This normally will be used to call to a super constructor
     * from the current class:
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>
     * Statement callSuper = A.STMT
     *     .ctorSuperS(
     *         A.EXPR.constX(1),
     *         A.EXPR.constX(2));
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>super(1,2)</code></pre>
     *
     * @param args
     * @return an instance of type {@link Statement}
     * @since 0.1.0
     */
    public static Statement ctorSuperS(final Expression... args) {
        return GeneralUtils.ctorSuperS(new ArgumentListExpression(args));
    }

    /**
     * Returns an  instance of {@link BlockStatement}.
     *
     * @param statements a list of type Statement
     * @return an instance of {@link BlockStatement}
     * @since 0.1.0
     */
    public static BlockStatement blockS(final List<Statement> statements) {
        return GeneralUtils.block(statements.toArray(new Statement[statements.size()]));
    }

    /**
     * Returns an  instance of {@link BlockStatement}.
     *
     * @param statements a varargs of type Statement
     * @return an instance of {@link BlockStatement}
     * @since 0.1.0
     */
    public static BlockStatement blockS(final Statement... statements) {
        return GeneralUtils.block(statements);
    }

    /**
     * Returns an  instance of {@link BlockStatement}.
     *
     * @param variableScope scope containing ref/local variables
     * @param statements a varargs of type Statement
     * @return an instance of {@link BlockStatement}
     * @since 0.1.5
     */
    public static BlockStatement blockS(final VariableScope variableScope, final Statement... statements) {
        return GeneralUtils.block(variableScope, statements);
    }

    /**
     * Returns an  instance of {@link BlockStatement}.
     *
     * @param variableScope scope containing ref/local variables
     * @param statements a list of type Statement
     * @return an instance of {@link BlockStatement}
     * @since 0.1.5
     */
    public static BlockStatement blockS(final VariableScope variableScope, final List<Statement> statements) {
        final Statement[] stmtArray = new Statement[statements.size()];

        return GeneralUtils.block(variableScope, statements.toArray(stmtArray));
    }

    /**
     * Returns an  instance of {@link BlockStatement}. The code contained
     * in the string passed as argument will be parsed and converted
     * to a {@link BlockStatement}
     *
     * @param code the string representation of the code
     * @return an instance of {@link BlockStatement}
     * @since 0.1.0
     */
    public static BlockStatement blockSFromString(final String code) {
        return (BlockStatement) new AstBuilder().buildFromString(code).get(0);
    }

    /**
     * Returns an instance of {@link AssertStatement}
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>assertS(booleanExpr)</code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>assert 1 == 1</code></pre>
     *
     * @param booleanExpr the expression we want to check
     * @return an instance of {@link AssertStatement}
     * @since 0.1.5
     */
    public static AssertStatement assertS(final BooleanExpression booleanExpr) {
        return new AssertStatement(booleanExpr, A.EXPR.constX("Compilation assertion error"));
    }

    /**
     * Returns an instance of {@link AssertStatement}
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>assertS(booleanExpr, "checking something important")</code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>assert 1 == 1 : "checking something important"</code></pre>
     *
     * @param booleanExpr the expression we want to check
     * @param errorMessage the error message in case assertion fails
     * @return an instance of {@link AssertStatement}
     * @since 0.1.5
     */
    public static AssertStatement assertS(final BooleanExpression booleanExpr, final String errorMessage) {
        return new AssertStatement(booleanExpr, A.EXPR.constX(errorMessage));
    }

    /**
     * Returns an instance of {@link TryCatchStatementBuilder} to
     * build complex try/catch statements the easy way. Check {@link
     * TryCatchStatementBuilder} javadoc to know how to use it.
     *
     * @return an instance of {@link TryCatchStatement}
     * @see TryCatchStatementBuilder
     * @since 0.2.3
     */
    public static TryCatchStatementBuilder tryCatchSBuilder(){
        return new TryCatchStatementBuilder();
    }

    /**
     * Builds a if statement, without the else part. It receives a
     * boolean expression and the code executed in case the boolean
     * expression evaluates to true<br><br>
     *
     * <strong>AST</strong>
     * <pre><code>ifS(boolX('a', Types.COMPARE_GREATER_THAN, 'b'), codeStmt)</code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>if (a > b) {
     *  // content of the codeStmt statement
     * }</code></pre>
     *
     * @param booleanExpr the boolean expression used to decide
     * whether the next statement will be executed or not
     * @param ifStmt code that will be executed if the boolean
     * expression evaluates to true
     * @since 0.2.4
     */
    public static IfStatement ifS(final BooleanExpression booleanExpr, final Statement ifStmt) {
        return new IfStatement(booleanExpr, ifStmt, emptyStatement());
    }

    /**
     * Represents a do-while statement. A do while loop is a control
     * flow statement that executes a block of code at least once, and
     * then repeatedly executes the block, or not, depending on a
     * given boolean condition at the end of the block
     *
     * @param booleanExpr boolean condition
     * @param loopBlock the block that could be repeated
     * @return an instance of type {@link DoWhileStatement}
     * @deprecated As of release 2.4.7, replaced by {@link #doWhileStmt()}
     * @since 0.2.6
     */
    @Deprecated
    public static DoWhileStatement doWhileStatement(final BooleanExpression booleanExpr, final Statement loopBlock) {
        return new DoWhileStatement(booleanExpr, loopBlock);
    }

    /**
     * Represents a do-while statement. A do while loop is a control
     * flow statement that executes a block of code at least once, and
     * then repeatedly executes the block, or not, depending on a
     * given boolean condition at the end of the block
     *
     * @param booleanExpr boolean condition
     * @param loopBlock the block that could be repeated
     * @return an instance of type {@link DoWhileStatement}
     * @since 0.2.7
     */
    public static DoWhileStatement doWhileS(final BooleanExpression booleanExpr, final Statement loopBlock) {
        return new DoWhileStatement(booleanExpr, loopBlock);
    }

    /**
     * Represents a while statement. A while loop is a control flow
     * statement that executes maybe repeatedly a code block,
     * depending on a given boolean condition.
     *
     * @param booleanExpr boolean condition
     * @param loopBlock the block that could be repeated
     * @return an instance of type {@link DoWhileStatement}
     * @since 0.2.7
     */
    public static WhileStatement whileS(final BooleanExpression booleanExpr, final Statement loopBlock) {
        return new WhileStatement(booleanExpr, loopBlock);
    }

    /**
     * Represents an empty statement. It could be used when building
     * an {@link IfStatement} with an empty else part.
     *
     * @return an empty {@link Statement}
     * @deprecated As of release 2.4.7, replaced by {@link #emptyS()}
     * @since 0.2.4
     */
    public static Statement emptyStatement() {
        return A.STMT.stmt(A.EXPR.constX(""));
    }

    /**
     * Represents an empty statement. It could be used when building
     * an {@link IfStatement} with an empty else part.
     *
     * @return an empty {@link Statement}
     * @since 0.2.7
     */
    public static Statement emptyS() {
        return A.STMT.stmt(A.EXPR.constX(""));
    }

    /**
     * Builds a if-else statement. It receives a boolean expression
     * and two statements corresponding to the both if and else
     * parts<br><br>
     *
     * <strong>AST</strong>
     * <pre><code>ifElseS(boolX('a', Types.COMPARE_GREATER_THAN, 'b'), ifStmt, elseStmt)</code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>if (a > b) {
     *  // ifStmt code
     * } else {
     *  // elseStmt
     * }</code></pre>
     *
     * @param booleanExpr the boolean expression used to decide
     * whether the next statement will be executed or not
     * @param ifStmt code that will be executed if the boolean
     * expression evaluates to true
     * @since 0.2.4
     */
    public static IfStatement ifElseS(final BooleanExpression booleanExpr, final Statement ifStmt, final Statement elseStmt) {
        return new IfStatement(booleanExpr, ifStmt, elseStmt);
    }

    /**
     * Represents how to throw an exception
     *
     * <strong>AST</strong>
     * <pre><code>throwS(newX(IllegalStateException, constX('wrong value')))</code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>throw new IllegalStateException('wrong value')</code></pre>
     *
     * @param expression it will be normally a representation of a new
     * instance of a given Throwable type
     * @return an instance of {@link ThrowStatement}
     * @since 0.2.4
     */
    public static ThrowStatement throwS(final Expression expression) {
        return new ThrowStatement(expression);
    }
}
