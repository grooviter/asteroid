package asteroid.local.samples

import asteroid.A
import asteroid.AbstractLocalTransformation
import asteroid.Phase
import org.codehaus.groovy.ast.AnnotatedNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.FieldNode

/**
 * Checks whether an {@link AnnotatedNode} follows the defined pattern in the
 * pattern property
 *
 * @since 0.2.5
 */
@Phase(Phase.LOCAL.INSTRUCTION_SELECTION)
class NameCheckerImpl extends AbstractLocalTransformation<NameChecker, AnnotatedNode> {
    @Override
    void doVisit(AnnotationNode annotation, AnnotatedNode annotated) {
        String pattern = A.UTIL.ANNOTATION.getStringValue(annotation)
        String nodeText = getName(annotated)
        Boolean matches = nodeText ==~ pattern

        if (!matches) {
            addError 'Pattern doesn\'t match annotated name', annotated
        }
    }

    String getName(ClassNode classNode){
        return classNode.name
    }

    String getName(FieldNode fieldNode) {
        return fieldNode.name
    }

    String getName(AnnotatedNode annotatedNode) {
        addError "Pattern doesn't match annotated name", annotatedNode
    }
}
