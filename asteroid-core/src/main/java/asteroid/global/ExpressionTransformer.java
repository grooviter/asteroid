package asteroid.global;

import groovy.lang.Closure;

import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;

/**
 * This {@link Transformer} could be used as a base for transforming
 * {@link Expression} instances.
 *
 * <b>IMPORTANT:</b> The parameter type is only used as a hint. If you
 * are not carefull on how you define the search criteria you could
 * get a {@link ClassCastException} at runtime. The criteria should ask
 * for the type of the expression in the first place.
 *
 * @param <T> use as a hint for the {@link ExpressionTransformer#transformExpression} method parameter
 * @since 0.1.2
 */
public abstract class ExpressionTransformer<T extends Expression> extends Transformer {

    private final Closure<Boolean> criteria;;

    /**
     * Every instance needs the source unit awareness and the name of the method
     * it's going to transform
     *
     * @param sourceUnit Needed to apply scope
     * @param criteria the criteria used to search the interesting
     * {@link Expression}
     * @since 0.1.2
     */
    public ExpressionTransformer(SourceUnit sourceUnit, Closure<Boolean> criteria) {
        super(sourceUnit);
        this.criteria = criteria;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression transform(Expression expression) {
        if (expression == null) {
            return null;
        }

        boolean proceed = criteria.call(expression);

        return proceed ? this.transformExpression((T) expression) : expression.transformExpression(this);
    }

    /**
     * This method will transform the expression into its final version.
     *
     * @param expression the method expression you want to transform
     * @return the final version of the method expression
     * @since 0.1.2
     */
    public abstract Expression transformExpression(T expression);

    /**
     * This method returns a criteria to look for {@link MethodCallExpression}
     * with a name equals to the name passed as parameter
     *
     * @param name the method name
     * @return a search criteria
     * @since 0.1.2
     */
    public static Closure<Boolean> methodCallByNameEq(final String name) {
        return new Closure<Boolean>(null) {
            public Boolean doCall(Expression expression) {
                if (!(expression instanceof MethodCallExpression)) {
                    return false;
                }

                MethodCallExpression expr = (MethodCallExpression) expression;

                return expr.getMethodAsString().equals(name);
            }
        };
    }

    /**
     * This criteria will make the transformer to process every {@link Expression}
     *
     * @return a criteria to process everything
     * @since 0.1.5
     */
    public static Closure<Boolean> everything() {
        return new Closure<Boolean>(null) {
            public Boolean doCall(Expression expression) {
                return true;
            }
        };
    }
}
