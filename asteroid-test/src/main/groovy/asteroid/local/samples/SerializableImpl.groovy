package asteroid.local.samples

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.control.SourceUnit

import asteroid.A
import asteroid.local.LocalTransformation
import asteroid.local.LocalTransformationImpl

@CompileStatic
@LocalTransformation(A.PHASE_LOCAL.INSTRUCTION_SELECTION)
class SerializableImpl extends LocalTransformationImpl<Serializable, ClassNode> {

    @Override
    void doVisit(AnnotationNode annotation, ClassNode classNode, SourceUnit source) {
        check: 'package starts with asteroid'
        classNode.packageName.startsWith('asteroid') // <1>

        check: 'there are least than 2 methods'
        classNode.methods.size() < 2 // <2>

        then: 'make it implements Serializable and Cloneable'
        A.UTIL.CLASS.addInterfaces(classNode, java.io.Serializable, Cloneable) // <3>
    }
}
