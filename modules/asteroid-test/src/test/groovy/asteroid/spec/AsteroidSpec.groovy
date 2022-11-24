package asteroid.spec

import static org.codehaus.groovy.control.CompilePhase.INSTRUCTION_SELECTION

import spock.lang.Specification
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.tools.ast.TransformTestHelper

/**
 * @since 0.1.2
**/
class AsteroidSpec extends Specification {

    static final BASE = "./src/test/groovy/"

    /**
     * This method helps to create a new class instance to be able to
     * test the class that uses the transformation
     *
     * @param transformationClass The name of the AST transformation we want to test
     * @return a class of the class that contains the transformation
    **/
    def getClassToTest(Class transformationClass){
        getClassToTestForPhase(transformationClass, CompilePhase.INSTRUCTION_SELECTION)
    }

    def getClassToTestForPhase(Class transformationClass, CompilePhase compilePhase) {
        TransformTestHelper invoker =
            getScriptParser(transformationClass, compilePhase)

        def qualifiedName = getClass().name.replaceAll("\\.", "\\/")
        def file = new File("${BASE}${qualifiedName}Example.groovy")

        /* The class we want to test */
        return invoker.parse(file)
    }

    TransformTestHelper getScriptParser(Class transformationClass, CompilePhase compilePhase) {
        return new TransformTestHelper(
            transformationClass.newInstance(),
            compilePhase
        )
    }

}
