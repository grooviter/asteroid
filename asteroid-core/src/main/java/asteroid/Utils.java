package asteroid;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.expr.MethodCallExpression;

import asteroid.utils.ClassNodeUtils;
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
     * Access to utility functions to deal with {@link MethodCallExpression} instances
     *
     * @since 0.1.4
     */
    public static final MethodCallExpressionUtils METHODX = new MethodCallExpressionUtils();

    /**
     * Access to utility functions to deal with general tasks
     *
     * @since 0.1.4
     */
    public static final MiscellaneousUtils MISC = new MiscellaneousUtils();

}
