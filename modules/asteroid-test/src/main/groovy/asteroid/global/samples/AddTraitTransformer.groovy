package asteroid.global.samples


import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.CompilePhase

import asteroid.A
import asteroid.transformer.AbstractClassNodeTransformer

/**
 * This transformer looks for a class node annotated by
 * an annotation named 'TrickOrTrait' and makes that class node
 * to implement the trait passed as argument of the annotation.
 *
 * @since 0.1.7
 */
class AddTraitTransformer extends AbstractClassNodeTransformer {

    static final String ANNOTATION_NAME = 'TrickOrTrait'

    AddTraitTransformer(final SourceUnit sourceUnit) {
        super(sourceUnit,
              A.CRITERIA.byAnnotationSimpleName(ANNOTATION_NAME))
    }

    @Override
    void transformClass(final ClassNode target) {
        AnnotationNode annotation = A.UTIL.NODE.getAnnotationFrom(target, ANNOTATION_NAME)
        String              value = A.UTIL.NODE.getStringValue(annotation)
        ClassNode       traitType = A.NODES.clazz(value).build()

        println "AddTraitTransformer is applied at: ${CompilePhase.fromPhaseNumber(sourceUnit.phase)} phase"

        A.UTIL.NODE.addInterfaces(target, traitType)
    }
}
