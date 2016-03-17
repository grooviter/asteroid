package asteroid.global.samples

import groovy.transform.CompileStatic

import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MethodCallExpression

import asteroid.A
import asteroid.global.ExpressionTransformer

/**
 * This {@link ExpressionTransformer} transforms {@link MethodCallExpression}
 * instances with name 'xxx' to a constant number expression 1.
 *
 * @since 0.1.2
 */
@CompileStatic
class ChangeTripleXToPlusOne extends ExpressionTransformer<MethodCallExpression> {

    ChangeTripleXToPlusOne(final SourceUnit sourceUnit) {
        super(sourceUnit, methodCallByNameEq('xxx'))
    }

    Expression transformExpression(final MethodCallExpression target) {
        return A.EXPR.constX(1)
    }
}
