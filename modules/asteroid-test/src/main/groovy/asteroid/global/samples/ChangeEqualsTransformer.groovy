package asteroid.global.samples

import groovy.transform.CompileStatic

import asteroid.A
import asteroid.transformer.AbstractExpressionTransformer
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.control.SourceUnit

@CompileStatic
class ChangeEqualsTransformer extends AbstractExpressionTransformer<BinaryExpression> {

    ChangeEqualsTransformer(SourceUnit sourceUnit) {
        super(BinaryExpression, sourceUnit)
    }

    @Override
    Expression transformExpression(BinaryExpression expression) {
        return A.EXPR.callX(expression.leftExpression, 'equals', expression.rightExpression)
    }
}
