package asteroid.global.samples

import static org.codehaus.groovy.control.CompilePhase.CONVERSION
import asteroid.spec.AsteroidSpec

class AddTransformerSpec extends AsteroidSpec {

    void 'add field to Class'() {
        given: 'an instance of the transformed class'
        def instance = transformedClass.newInstance()

        when: 'executing the transformed code'
        def result = instance.execute()

        then: 'we should get the expected result'
        result

        and:
        result instanceof java.io.Serializable
    }

    private Class getTransformedClass() {
        return getClassToTestForPhase(AddTransformation, CONVERSION)
    }
}
