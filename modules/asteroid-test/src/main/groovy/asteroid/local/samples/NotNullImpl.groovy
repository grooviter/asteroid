package asteroid.local.samples

import asteroid.A
import asteroid.AbstractLocalTransformation
import asteroid.Phase
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ConstructorNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.stmt.Statement

@Phase(Phase.LOCAL.INSTRUCTION_SELECTION)
class NotNullImpl extends AbstractLocalTransformation<NotNull, ConstructorNode> {
    @Override
    void doVisit(AnnotationNode annotation, ConstructorNode annotated) {
        List<Statement> params = annotated.parameters.collect(this.&createCheckFor)
        Statement newStatement = A.STMT.blockS(params)

        A.UTIL.NODE.addBeforeCodeBlock(annotated, newStatement)
    }

    IfStatement createCheckFor(Parameter parameter) {
        return A.STMT.ifS(
                A.EXPR.boolIsTrueX(A.EXPR.varX(parameter.name)),
                A.STMT.stmt(A.EXPR.newX(IllegalStateException, A.EXPR.constX('null value!!'))))
    }
}
