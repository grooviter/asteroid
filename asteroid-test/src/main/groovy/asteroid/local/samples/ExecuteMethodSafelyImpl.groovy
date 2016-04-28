package asteroid.local.samples

import asteroid.A
import asteroid.local.LocalTransformation
import asteroid.local.AbstractLocalTransformation

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.FieldNode

@CompileStatic
@LocalTransformation(A.PHASE_LOCAL.SEMANTIC_ANALYSIS)
class ExecuteMethodSafelyImpl extends AbstractLocalTransformation<ExecuteMethodSafely, MethodNode> {

    @Override
    void doVisit(AnnotationNode annotation, MethodNode methodNode) {
        List<FieldNode> fields = A.UTIL.CLASS.getInstancePropertyFields(methodNode.declaringClass)
        String valueFieldNode = fields.find()?.name

        methodNode.code = A.STMT.returnS(A.EXPR.safeCallX(A.EXPR.varX(valueFieldNode), 'toString'))
    }
}
