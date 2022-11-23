package asteroid.global.samples

import static org.codehaus.groovy.control.CompilePhase.CANONICALIZATION
import asteroid.spec.AsteroidSpec

class ChangeMethodsSpec extends AsteroidSpec {

    void 'xxx to 1'() {
        given: 'an instance of the transformed class'
        def instance = transformedClass.newInstance()

        when: 'executing the transformed code'
        Integer result = instance.execute()

        then: 'we should get the expected result'
        result == 2
    }

    private Class getTransformedClass() {
        return getClassToTestForPhase(ChangeMethodsTransformation, CANONICALIZATION)
    }
}
