package asteroid.utils

import static org.codehaus.groovy.control.CompilePhase.CONVERSION

import asteroid.spec.AsteroidSpec

class CollectLabelsSpec extends AsteroidSpec {

    void 'get stament labels'() {
        given: 'an instance of the transformed class'
        def instance = transformedClass.newInstance()

        when: 'executing the method'
        def result = instance.execute()

        then: 'method should return label names'
        result == [first:'john', second:'peter', third:'paul', fourth: [name: "paul", age: 22]]
    }

    private Class getTransformedClass() {
        return getClassToTestForPhase(CollectLabelsImpl, CONVERSION)
    }
}
