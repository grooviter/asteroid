package asteroid.global;

import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;

import org.codehaus.groovy.control.SourceUnit;

/**
 * This class can be used as a base to transform certain MethodCallExpression instances.
 *
 * Let's say we had a expression like:
 * <pre>inventedmethod(x:1) {}</pre>
 * We can transform that expression into another creating an instance of a MethodCallExpression:
 * <pre>
 * class InventedMethodTransformer extends MethodCallExpressionTransformer {
 *       InventedMethodTransformer(SourceUnit sourceUnit) {
 *           super(sourceUnit, 'inventedMethod')
 *       }
 *
 *       Expression transformMethodCall(MethodCallExpression methodCallExpression) {
 *           // implement the transformation
 *       }
 * }
 * </pre>
 *
 * @since 0.1.1
 */
abstract class MethodCallExpressionTransformer extends AbstractClassCodeExpressionTransformer {

    private final String methodCallName;

    /**
     * Every instance needs the source unit awareness and the name of the method
     * it's going to transform
     *
     * @param sourceUnit Needed to apply scope
     * @param name The name of the method you want to transform
     * @since 0.1.1
     */
    public MethodCallExpressionTransformer(SourceUnit sourceUnit, String name) {
        super(sourceUnit);
        this.methodCallName = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression transform(Expression expression) {
        if (expression == null) return null;

        boolean transformable = isTransformable(expression);

        return transformable ?
            transformMethodCall((MethodCallExpression) expression) :
            expression.transformExpression(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTransformable(final Expression expression) {
        if (expression instanceof MethodCallExpression) {
            MethodCallExpression methodCall = (MethodCallExpression) expression;

            return methodCall.getMethodAsString() == this.methodCallName;
        }

        return false;
    }

    /**
     * This method will transform the expression into its final version.
     *
     * @param methodCallExpression the method expression you want to transform
     * @return the final version of the method expression
     * @since 0.1.1
     */
    public abstract Expression transformMethodCall(MethodCallExpression methodCallExpression);

}
