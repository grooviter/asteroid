package asteroid.local.samples

import asteroid.A
import asteroid.Phase
import asteroid.AbstractLocalTransformation

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.FieldNode

@CompileStatic
@Phase(Phase.LOCAL.SEMANTIC_ANALYSIS)
class ExecuteMethodSafelyImpl extends AbstractLocalTransformation<ExecuteMethodSafely, MethodNode> {

    @Override
    void doVisit(AnnotationNode annotation, MethodNode methodNode) {
        List<FieldNode> fields = A.UTIL.NODE.getInstancePropertyFields(methodNode.declaringClass)
        String valueFieldNode = fields.find()?.name

        methodNode.code = A.STMT.returnS(A.EXPR.safeCallX(A.EXPR.varX(valueFieldNode), 'toString'))
    }
}
