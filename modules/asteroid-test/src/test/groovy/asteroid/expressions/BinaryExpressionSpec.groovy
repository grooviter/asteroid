package asteroid.expressions

import asteroid.spec.AsteroidSpec

class BinaryExpressionSpec extends AsteroidSpec {

    void 'using a good number'() {
        setup:
        def testInstance = getClassToTest(AddOneToParamImpl).newInstance()

        when: 'executing the calculation'
        def result = testInstance.executeCalculation(13)

        then: 'we should get the expected number'
        result == 28
    }

    void 'using the wrong number'() {
        setup: 'setting magic number and the example under test'
        def magicNumber = 42
        def testInstance = getClassToTest(AddOneToParamImpl).newInstance()

        when: 'executing the calculation'
        def result = testInstance.executeCalculation(magicNumber)

        then: 'we should get an exception because we are using the magic number'
        thrown(IllegalStateException)
    }
}
