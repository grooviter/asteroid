package examples

import asteroid.A
import asteroid.local.LocalTransformation
import asteroid.local.LocalTransformationImpl
import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.control.SourceUnit

@CompileStatic
@LocalTransformation(A.PHASE_LOCAL.SEMANTIC_ANALYSIS) // <1>
class WithLoggingTransformationImpl extends LocalTransformationImpl<WithLogging, MethodNode> {
    @Override
    void doVisit(AnnotationNode annotation, MethodNode methodNode, SourceUnit source) {
        def oldCode   = methodNode.code // <2>
        def startCode = A.STMT.stmt(printlnX("start")) // <3>
        def endCode   = A.STMT.stmt(printlnX("end")) // <4>

        methodNode.code = A.STMT.blockS(startCode, oldCode, endCode) // <5>
    }

    Expression printlnX(String message) {
        return A.EXPR.callThisX("println", A.EXPR.constX(message)) // <6>
    }
}
