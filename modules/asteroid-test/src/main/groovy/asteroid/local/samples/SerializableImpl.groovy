package asteroid.local.samples

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.AnnotationNode

import asteroid.A
import asteroid.Phase
import asteroid.AbstractLocalTransformation

@CompileStatic
@Phase(Phase.LOCAL.INSTRUCTION_SELECTION)
class SerializableImpl extends AbstractLocalTransformation<Serializable, ClassNode> {

    @Override
    void doVisit(AnnotationNode annotation, ClassNode classNode) {
        check: 'package starts with asteroid'
        classNode.packageName.startsWith('asteroid') // <1>

        check: 'there are least than 2 methods'
        classNode.methods.size() < 2 // <2>

        then: 'make it implements Serializable and Cloneable'
        A.UTIL.NODE.addInterfaces(classNode, java.io.Serializable, Cloneable) // <3>
    }
}
