package asteroid.local.samples.criterias

import asteroid.A
import asteroid.Phase
import asteroid.AbstractLocalTransformation

import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.AnnotationNode

@Phase(Phase.LOCAL.INSTRUCTION_SELECTION) // <1>
class CheckImportantMethodsImpl extends AbstractLocalTransformation<CheckImportantMethods, ClassNode> {

    @Override
    void doVisit(AnnotationNode annotation, ClassNode classNode) {
        // tag::criteriasaspredicates[]
        List<MethodNode> notSoImportantMethods = classNode
            .methods
            .findAll(A.CRITERIA.and(A.CRITERIA.byAnnotation(Important),
                                    A.CRITERIA.byMethodNodeNameStartsWith('get')))
        // end::criteriasaspredicates[]

        String userHome = System.getProperty('user.home')
        String fileName = "${classNode.nameWithoutPackage.toLowerCase()}.txt"
        File analysisFile = new File(userHome, fileName)
        String analysisText = notSoImportantMethods.inject("") { acc, val ->
            acc += "${val.name}\n"
        }

        analysisFile.text = analysisText
    }
}
