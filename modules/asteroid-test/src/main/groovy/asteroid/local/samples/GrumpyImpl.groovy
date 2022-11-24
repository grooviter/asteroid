package asteroid.local.samples

import asteroid.Phase
import asteroid.AbstractLocalTransformation

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.AnnotationNode

@CompileStatic
@Phase(Phase.LOCAL.SEMANTIC_ANALYSIS)
class GrumpyImpl extends AbstractLocalTransformation<Grumpy, ClassNode> {

    @Override
    void doVisit(AnnotationNode annotation, ClassNode clazz) {
        addError("I don't like you Argggg!!!!! (said the Grumpy transformation)", clazz)
    }
}
