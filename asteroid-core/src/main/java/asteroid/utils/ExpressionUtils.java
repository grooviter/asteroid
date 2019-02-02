package asteroid.utils;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.last;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.first;

import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.syntax.Types;
import groovy.lang.Closure;
import java.util.List;

/**
 * Utility classes to deal with {@link Expression} instances
 *
 * @since 0.3.0
 * @see asteroid.Utils
 */
public final class ExpressionUtils {

    /**
     * Given a {@link MethodCallExpression} it returns whether it has arguments
     * or not
     *
     * @param methodCallExpr a method call we want the arguments from
     * @return true if the method has any argument false otherwise
     * @since 0.3.0
     */
    public boolean hasArguments(final MethodCallExpression methodCallExpr) {
        return getArgumentList(methodCallExpr).getExpressions().isEmpty();
    }

    /**
     * Given a {@link MethodCallExpression} it returns a list of arguments
     *
     * @param methodCallExpr a method call we want the arguments from
     * @return a list of expressions within a {@link ArgumentListExpression}
     * @since 0.1.4
     */
    public ArgumentListExpression getArgumentList(final MethodCallExpression methodCallExpr) {
        return (ArgumentListExpression) methodCallExpr.getArguments();
    }

    /**
     * Return the first element of the {@link ArgumentListExpression}
     * passed as parameters as the expected type.
     *
     * @param methodCallExpr a method call we want the arguments from
     * @param asType the expected type
     * @return the first argument casted as the expected type
     * @since 0.3.0
     */
    public <U extends Expression> U getFirstArgumentAs(final MethodCallExpression methodCallExpr, final Class<U> asType) {
        return asType.cast(first(getArgumentList(methodCallExpr).getExpressions()));
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
     * @param methodCallExpr a method call we want the arguments from
     * @param index the index where the expression is located
     * @param asType the {@link Class} of the expected expression
     * @return the expression found in the expression list using the
     * provided index or null if it couldn't be found
     * @since 0.3.0
     */
    public <U extends Expression> U getArgumentByIndexAs(final MethodCallExpression methodCallExpr, final Integer index, final Class<U> asType) {
        final List<Expression> expressionList = getArgumentList(methodCallExpr).getExpressions();

        if (expressionList.isEmpty() || expressionList.size() < index) {
            return null;
        }

        return asType.cast(expressionList.get(index));
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
     * @param asType the expected type
     * @return the last argument casted as the expected type
     * @since 0.3.0
     */
    public <U extends Expression> U getLastArgumentAs(final MethodCallExpression methodCallExpr, final Class<U> asType) {
        return asType.cast(last(getArgumentList(methodCallExpr).getExpressions()));
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

    /**
     * Checks whether an expression is of type {@link MethodCallExpression}
     *
     * @param expr the evaluated expression
     * @return true if the expression is an instance of {@link MethodCallExpression}
     * @since 0.3.0
     */
    public boolean isMethodCallExpr(final Expression expr) {
        return expr instanceof MethodCallExpression;
    }

    /**
     * Checks whether an expression is of type {@link ListExpression}
     *
     * @param expr the evaluated expression
     * @return true if the expression is an instance of {@link ListExpression}
     * @since 0.3.0
     */
    public boolean isListExpr(final Expression expr) {
        return expr instanceof ListExpression;
    }

    /**
     * Checks whether an expression is of type {@link BinaryExpression}
     *
     * @param expr the evaluated expression
     * @return true if the expression is an instance of {@link BinaryExpression}
     * @since 0.3.0
     */
    public boolean isBinaryExpr(final Expression expr) {
        return expr instanceof BinaryExpression;
    }

    /**
     * Checks whether an expression is of type {@link BinaryExpression} using
     * a specific binary operator
     *
     * @param expr the evaluated expression
     * @param operator int value taken from {@link Types}
     * @return true if the expression is an instance of {@link BinaryExpression}
     * @since 0.3.0
     */
    public boolean isBinaryExpr(final Expression expr, final int operator) {
        return isBinaryExpr(expr) && asBinaryExpr(expr).getOperation().getType() == operator;
    }

    /**
     * Executes a given computation if the expression is of type
     * {@link BinaryExpression} if not, then a default value is
     * provided
     *
     * @param expr the expression we want to compute over
     * @param defaultValue a value in case the expression is not of type {@link BinaryExpression}
     * @param func function executed in case expression is not of type {@link BinaryExpression}
     * @return the result of the computation or the default value
     * @since 0.3.0
     */
    public <T> T asBinaryExpr(final Expression expr, final T defaultValue, final Closure<T> func) {
        return isBinaryExpr(expr) ? func.call(asBinaryExpr(expr)) : defaultValue;
    }

    /**
     * Unsafe cast of a given @{link Expression} to a {@link BinaryExpression}
     *
     * @param expr the expression we want to cast
     * @return the expression cast as a {@link BinaryExpression}
     * @since 0.3.0
     */
    public BinaryExpression asBinaryExpr(final Expression expr) {
        return (BinaryExpression) expr;
    }

    /**
     * From a {@link BinaryExpression} gets the left expression as if
     * it were of a given type
     *
     * @param binaryExpr the binary expression we want the left expr from
     * @param type the type we would like to cast the left expr to
     * @return the left expression cast to a given type
     * @since 0.3.0
     */
    public <T> T leftExprAs(final BinaryExpression binaryExpr, final Class<T> type) {
        return type.cast(binaryExpr.getLeftExpression());
    }

    /**
     * From a {@link BinaryExpression} gets the right expression as if
     * it were of a given type
     *
     * @param binaryExpr the binary expression we want the right expr from
     * @param type the type we would like to cast the right expr to
     * @return the right expression cast to a given type
     * @since 0.3.0
     */
    public <T> T rightExprAs(final BinaryExpression binaryExpr, final Class<T> type) {
        return type.cast(binaryExpr.getRightExpression());
    }

    /**
     * Extracts the String value of a given expression
     *
     * @param expression expression to get text from
     * @return a {@link String} of a given {@link Expression}
     * @since 0.3.2
     */
    public String getText(final Expression expression) {
        if (expression == null) {
            return "";
        }

        if (expression instanceof ConstantExpression) {
            return ((ConstantExpression) expression).getText();
        }

        return expression.toString();
    }
}
