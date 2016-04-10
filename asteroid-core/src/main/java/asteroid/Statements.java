package asteroid;

import java.util.List;

import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.stmt.AssertStatement;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.builder.AstBuilder;
import org.codehaus.groovy.ast.expr.BooleanExpression;

import asteroid.A;

/**
 * This class hides the different implementations to create expressions through the Groovy api to provide a unified
 * an easier way to create statements when coding an AST transformation.
 *
 * @since 0.1.0
 */
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
        Statement[] stmtArray = new Statement[statements.size()];

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
    public static BlockStatement blockSFromString(String code) {
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
    public static AssertStatement assertS(BooleanExpression booleanExpr) {
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
}
