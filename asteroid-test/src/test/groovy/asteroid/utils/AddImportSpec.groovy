package asteroid.utils

import static org.codehaus.groovy.control.CompilePhase.CONVERSION
import asteroid.spec.AsteroidSpec

class AddImportSpec extends AsteroidSpec {

    void 'adding import'() {
        given: 'an instance of the transformed class'
        def instance = transformedClass.newInstance()

        when: 'executing the transformed code'
        String result = instance.asJson(name: "john")

        then: 'we should get the expected result'
        result == /{"name":"john"}/
    }

    private Class getTransformedClass() {
        return getClassToTestForPhase(AddImportImpl, CONVERSION)
    }
}
