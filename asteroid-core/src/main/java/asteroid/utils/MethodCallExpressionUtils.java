package asteroid.utils;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.last;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.first;

import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;

/**
 * Utility classes to deal with {@link MethodCallExpression} instances
 *
 * @since 0.1.4
 * @see asteroid.Utils
 */
public final class MethodCallExpressionUtils {

    /**
     * Given a {@link MethodCallExpression} it returns a list of arguments
     *
     * @param methodCallExpression a method call we want the arguments from
     * @return a list of expressions within a {@link ArgumentListExpression}
     * @since 0.1.4
     */
    public ArgumentListExpression getArgs(final MethodCallExpression methodCallExpr) {
        return (ArgumentListExpression) methodCallExpr.getArguments();
    }

    /**
     * Return the first element of the {@link ArgumentListExpression}
     * passed as parameters as the expected type.
     *
     * @param args the list of arguments
     * @param asType the expected type
     * @return the first argument casted as the expected type
     * @since 0.1.4
     */
    public <U extends Expression> U getFirstArgumentAs(final ArgumentListExpression args, final Class<U> asType) {
        return asType.cast(first(args.getExpressions()));
    }

    /**
     * Return the last element of the {@link ArgumentListExpression}
     * passed as parameters as the expected type.
     *
     * @param args the list of arguments
     * @param asType the expected type
     * @return the last argument casted as the expected type
     * @since 0.1.4
     */
    public <U extends Expression> U getLastArgumentAs(final ArgumentListExpression args, final Class<U> asType) {
        return asType.cast(last(args.getExpressions()));
    }
}
