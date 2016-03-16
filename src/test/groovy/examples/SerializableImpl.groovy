package examples

import asteroid.A
import asteroid.LocalTransformation
import asteroid.LocalTransformationImpl

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.control.SourceUnit

@CompileStatic
@LocalTransformation(A.PHASE_LOCAL.SEMANTIC_ANALYSIS)
class SerializableImpl extends LocalTransformationImpl<Serializable, ClassNode> {

    @Override
    void doVisit(AnnotationNode annotation, ClassNode node, SourceUnit source) {
        A.UTIL.addInterfaces(node, java.io.Serializable, Cloneable)
    }
}
