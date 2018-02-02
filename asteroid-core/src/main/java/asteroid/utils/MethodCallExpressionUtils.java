package asteroid.utils;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.last;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.first;

import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;

import java.util.List;

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
     * @param methodCallExpr a method call we want the arguments from
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
     * Return the element in the specified index from the {@link
     * ArgumentListExpression} passed as argument as the expected
     * type.
     *
     * @param args the argument list expression we want the argument from
     * @param index the index where the expression is located
     * @param asType the {@link Class} of the expected expression
     * @return the expression found in the expression list using the
     * provided index or null if it couldn't be found
     * @since 0.2.8
     */
    public <U extends Expression> U getArgumentByIndexAs(final ArgumentListExpression args, final Integer index, final Class<U> asType) {
        final List<Expression> expressionList = args.getExpressions();

        if (expressionList.isEmpty() || expressionList.size() < index) {
            return null;
        }

        return asType.cast(expressionList.get(index));
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
