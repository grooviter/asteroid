package asteroid;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.expr.MethodCallExpression;

import asteroid.utils.NodeUtils;
import asteroid.utils.CheckUtils;
import asteroid.utils.ClassNodeUtils;
import asteroid.utils.StatementUtils;
import asteroid.utils.MiscellaneousUtils;
import asteroid.utils.AnnotationNodeUtils;
import asteroid.utils.MethodCallExpressionUtils;

/**
 * This class gathers together a bunch of util functions to deal with
 * AST transformations.
 *
 * @since 0.1.0
 */
public final class Utils {

    /**
     * Access to utility functions to deal with {@link AnnotationNode} instances
     *
     * @since 0.1.4
     */
    public static final AnnotationNodeUtils ANNOTATION = new AnnotationNodeUtils();

    /**
     * Access to utility functions to deal with {@link ClassNode} instances
     *
     * @since 0.1.4
     */
    public static final ClassNodeUtils CLASS = new ClassNodeUtils();

    /**
     * Access to utility functions to deal with {@link ASTNode} instances
     *
     * @since 0.1.4
     */
    public static final NodeUtils NODE = new NodeUtils();

    /**
     * Access to utility functions to deal with {@link MethodCallExpression} instances
     *
     * @since 0.1.4
     */
    public static final MethodCallExpressionUtils METHODX = new MethodCallExpressionUtils();

    /**
     * Access to utility functions to deal with {@link Statement} instances
     *
     * @since 0.1.5
     */
    public static final StatementUtils STMT = new StatementUtils();

    /**
     * Access to utility functions to deal with general tasks
     *
     * @since 0.1.4
     */
    public static final MiscellaneousUtils MISC = new MiscellaneousUtils();

    /**
     * Access to utility functions to add checks to {@link MethodNode}
     *
     * @since 0.1.5
     */
    public static final CheckUtils CHECK = new CheckUtils();

}
