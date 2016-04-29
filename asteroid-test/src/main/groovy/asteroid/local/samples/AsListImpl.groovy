package asteroid.local.samples

import asteroid.A
import asteroid.LocalTransformation
import asteroid.AbstractLocalTransformation

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassNode

@CompileStatic
@LocalTransformation(A.PHASE_LOCAL.SEMANTIC_ANALYSIS) // <1>
class AsListImpl extends AbstractLocalTransformation<AsList, ClassNode> {

    @Override
    void doVisit(AnnotationNode annotation, ClassNode classNode) {
        classNode.superClass = A.NODES.clazz(ArrayList).build()
    }
}
