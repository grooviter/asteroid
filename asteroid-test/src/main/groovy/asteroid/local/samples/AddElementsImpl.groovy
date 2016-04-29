package asteroid.local.samples

import asteroid.A
import asteroid.LocalTransformation
import asteroid.AbstractLocalTransformation

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.PropertyNode
import org.codehaus.groovy.ast.expr.Expression

@CompileStatic
@LocalTransformation(A.PHASE_LOCAL.SEMANTIC_ANALYSIS)
class AddElementsImpl extends AbstractLocalTransformation<AsList, ClassNode> {

    @Override
    void doVisit(AnnotationNode annotation, ClassNode clazz) {
        A.UTIL.CLASS.addPropertyIfNotPresent(clazz, createPropertyFor(clazz))
        A.UTIL.CLASS.addMethodIfNotPresent(clazz, createMethod())
    }

    PropertyNode createPropertyFor(ClassNode owner) {
        return A.NODES.property('solution')
            .modifiers(A.ACC.ACC_PUBLIC)
            .type(String)
            .owner(owner)
            .initialValueExpression(A.EXPR.constX('42'))
            .build()
    }

    MethodNode createMethod() {
        return A.NODES.method('getSimpleTip')
            .modifiers(A.ACC.ACC_PUBLIC)
            .returnType(String)
            .code(A.STMT.returnS(A.EXPR.constX('No tips for you pal :P')))
            .build()
    }
}
