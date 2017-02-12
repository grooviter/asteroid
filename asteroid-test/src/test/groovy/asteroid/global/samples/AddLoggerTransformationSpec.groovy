package asteroid.global.samples

import static org.codehaus.groovy.control.CompilePhase.CONVERSION
import asteroid.spec.AsteroidSpec

class AddLoggerTransformationSpec extends AsteroidSpec {

    void 'execute controller index'() {
        given: 'an instance of the transformed class'
        def controller = transformedClass
            .newInstance()

        when: 'executing the transformed code'
        def indexResult = controller.index()

        then: 'getting expected view'
        indexResult == "index"
    }

    private Class getTransformedClass() {
        return getClassToTestForPhase(AddLoggerTransformation, CONVERSION)
    }
}
