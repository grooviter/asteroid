package asteroid

import static asteroid.A.CHECK
import static asteroid.A.EXPR
import static asteroid.A.STMT
import static asteroid.A.UTIL

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.control.SourceUnit

/**
 * This transformation is triggered when using the {@link Messenger} annotation. It
 * changes the annotated method body with the message declared in this type.
 */
@CompileStatic
@LocalTransformation(A.PHASE_LOCAL.INSTRUCTION_SELECTION)
class MessengerTransformationImpl extends LocalTransformationImpl<Messenger,MethodNode> {

    private static final String MESSAGE_ERR_STRANGE = "strange() method should not exist"
    private static final String MESSAGE_ERR_ANN_TYPE = "Annotation should be of type Messenger"

    @Override
    void doVisit(final AnnotationNode annotation, final MethodNode method, final SourceUnit sourceUnit) {
        Boolean badMethodIsThere   = method.declaringClass.methods.any { it.name == 'badMethod'}
        Boolean annotationIsOfType = A.UTIL.isOrImplements(annotation.classNode, Messenger)

        CHECK.source(sourceUnit) {
            checkThat method, hasName("saySomething")
            checkThat annotation, isAppliedOnceTo(method.declaringClass.methods)
            checkTrue annotation, annotationIsOfType, MESSAGE_ERR_ANN_TYPE
            checkFalse annotation, badMethodIsThere, MESSAGE_ERR_STRANGE
        }

        method.code = STMT.returnS(EXPR.constX(UTIL.getStringValue(annotation)));
    }

}
