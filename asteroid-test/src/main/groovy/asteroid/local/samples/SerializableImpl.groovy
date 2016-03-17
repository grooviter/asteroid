package asteroid.local.samples

import asteroid.A
import asteroid.local.LocalTransformation
import asteroid.local.LocalTransformationImpl

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.control.SourceUnit

@CompileStatic
@LocalTransformation(A.PHASE_LOCAL.SEMANTIC_ANALYSIS)
class SerializableImpl extends LocalTransformationImpl<Serializable, ClassNode> {

    @Override
    void doVisit(AnnotationNode annotation, ClassNode classNode, SourceUnit source) {
        A.CHECK.source(source) { // <1>
            checkTrue( // <2>
                classNode,
                classNode.packageName.startsWith("asteroid"),
                "Invalid State...because reasons!!")

            checkTrue( // <3>
                classNode,
                classNode.methods.size() < 2,
                "Invalid State...too many methods")
        }

        A.UTIL.addInterfaces(classNode, java.io.Serializable, Cloneable)
    }
}
