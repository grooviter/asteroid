package asteroid.global.samples

import groovy.transform.CompileStatic

import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MethodCallExpression

import asteroid.A
import asteroid.transformer.AbstractExpressionTransformer

@CompileStatic
// tag::expressiontransformer[]
class ChangeTripleXToPlusOne
    extends AbstractExpressionTransformer<MethodCallExpression> { // <1>

    ChangeTripleXToPlusOne(final SourceUnit sourceUnit) {
        super(MethodCallExpression, sourceUnit, methodCallByNameEq('xxx')) // <2>
    }

    @Override
    Expression transformExpression(final MethodCallExpression target) { // <3>
        return A.EXPR.constX(1)  // <4>
    }
}
// end::expressiontransformer[]
