package asteroid.local.samples

import asteroid.A
import asteroid.Phase
import asteroid.AbstractLocalTransformation

import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.AnnotationNode

@Phase(Phase.LOCAL.SEMANTIC_ANALYSIS) // <1>
class AsListImpl extends AbstractLocalTransformation<AsList, ClassNode> {

    @Override
    void doVisit(AnnotationNode annotation, ClassNode classNode) {
        classNode.superClass = A.NODES.clazz(ArrayList).build()
    }
}
