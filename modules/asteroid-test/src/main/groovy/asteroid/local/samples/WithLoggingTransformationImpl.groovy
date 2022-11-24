package asteroid.local.samples

import asteroid.A
import asteroid.Phase
import asteroid.AbstractLocalTransformation

import groovy.transform.CompileStatic

import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.stmt.Statement

@CompileStatic
@Phase(Phase.LOCAL.SEMANTIC_ANALYSIS) // <1>
class WithLoggingTransformationImpl extends AbstractLocalTransformation<WithLogging, MethodNode> {

    @Override
    void doVisit(final AnnotationNode annotation, final MethodNode methodNode) {
        def before = printlnS("start") // <2>
        def after = printlnS("end")   // <3>

        A.UTIL.NODE.addAroundCodeBlock(methodNode, before, after) // <4>
    }

    Statement printlnS(String message) {
        return A.STMT.stmt(A.EXPR.callThisX("println", A.EXPR.constX(message))) // <5>
    }
}
