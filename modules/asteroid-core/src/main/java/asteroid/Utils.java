package asteroid;

import asteroid.utils.NodeUtils;
import asteroid.utils.ExpressionUtils;
import asteroid.utils.StatementUtils;

/**
 * This class gathers together a bunch of util functions to deal with
 * AST transformations.
 *
 * @since 0.1.0
 */
public final class Utils {

    /**
     * Access to utility functions to deal with {@link org.codehaus.groovy.ast.ASTNode} instances
     *
     * @since 0.1.4
     */
    public static final NodeUtils NODE = new NodeUtils();

    /**
     * Access to utility functions to deal with {@link org.codehaus.groovy.ast.expr.Expression} instances
     *
     * @since 0.3.0
     */
    public static final ExpressionUtils EXPR = new ExpressionUtils();

    /**
     * Access to utility functions to deal with {@link org.codehaus.groovy.ast.stmt.Statement} instances
     *
     * @since 0.1.5
     */
    public static final StatementUtils STMT = new StatementUtils();
}
