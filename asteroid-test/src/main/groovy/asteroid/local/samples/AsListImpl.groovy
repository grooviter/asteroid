package asteroid.local.samples

import asteroid.A
import asteroid.local.LocalTransformation
import asteroid.local.LocalTransformationImpl

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.control.SourceUnit

@CompileStatic
@LocalTransformation(A.PHASE_LOCAL.SEMANTIC_ANALYSIS) // <1>
class AsListImpl extends LocalTransformationImpl<AsList, ClassNode> {

    @Override
    void doVisit(AnnotationNode annotation, ClassNode classNode, SourceUnit source) {
        classNode.superClass = A.NODES.clazz(ArrayList).build()
    }

}
