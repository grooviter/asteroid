package asteroid.transformer;

import groovy.lang.Closure;

import org.codehaus.groovy.syntax.Types;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;

/**
 * This {@link Transformer} could be used as a base for transforming
 * {@link Expression} instances.
 * <br><br>
 * <b class="warning">IMPORTANT:</b> The parameter type is only used as a hint. If you
 * are not carefull on how you define the search criteria you could
 * get a {@link ClassCastException} at runtime. The criteria should ask
 * for the type of the expression in the first place.
 *
 * @param <T> use as a hint for the {@link AbstractExpressionTransformer#transformExpression} method parameter
 * @since 0.2.0
 */
public abstract class AbstractExpressionTransformer<T extends Expression> extends AbstractTransformer {

    private final Closure<Boolean> criteria;
    private final Class<T> clazz;

    /**
     * Every instance needs the source unit awareness and the name of the method
     * it's going to transform
     *
     * @param clazz the type of the expression we're interested in
     * @param sourceUnit Needed to apply scope
     * @param criteria the criteria used to search the interesting
     * {@link Expression}
     * @since 0.2.3
     */
    public AbstractExpressionTransformer(final Class<T> clazz, final SourceUnit sourceUnit, final Closure<Boolean> criteria) {
        super(sourceUnit);
        this.clazz = clazz;
        this.criteria = criteria;
    }

    /**
     * Every instance needs the source unit awareness and the name of the method
     * it's going to transform
     *
     * @param clazz the type of the expression we're interested in
     * @param sourceUnit Needed to apply scope
     * {@link Expression}
     * @since 0.2.3
     */
    public AbstractExpressionTransformer(final Class<T> clazz, final SourceUnit sourceUnit) {
        super(sourceUnit);
        this.clazz = clazz;
        this.criteria = everything();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression transform(final Expression expression) {
        if (expression == null) {
            return null;
        }

        final boolean matches = isOfTypeAndMatchesCriteria(expression, clazz, criteria);

        if (matches) {
            return this.transformExpression((T) expression);
        }

        return expression.transformExpression(this);
    }

    private static boolean isOfTypeAndMatchesCriteria(final Expression expression, final Class clazz, final Closure<Boolean> predicate) {
        return isOfType(expression, clazz) && predicate.call(expression);
    }

    private static boolean isOfType(final Expression expression, final Class clazz) {
        return clazz != null && clazz.isInstance(expression);
    }

    /**
     * This method will transform the expression into its final version.
     *
     * @param expression the method expression you want to transform
     * @return the final version of the method expression
     * @since 0.2.0
     */
    public abstract Expression transformExpression(T expression);
    /**
     * This criteria will make the transformer to process every {@link Expression}
     *
     * @deprecated use {@link asteroid.Criterias}
     * @return a criteria to process everything
     * @since 0.2.0
     */
    @Deprecated
    public static Closure<Boolean> everything() {
        return new Closure<Boolean>(null) {
            public Boolean doCall(final Expression expression) {
                return true;
            }
        };
    }
}
