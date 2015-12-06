package asteroid;

import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;

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
     */
    public static Statement ctorSuperS(final Expression... args) {
        return GeneralUtils.ctorSuperS(new ArgumentListExpression(args));
    }

    public static BlockStatement block(final Statement... statements) {
        return GeneralUtils.block(statements);
    }

}
