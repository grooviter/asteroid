package asteroid.statements.trycatch

import spock.lang.Unroll
import asteroid.spec.AsteroidSpec

class TryCatchStatementSpec extends AsteroidSpec {

    @Unroll
    void 'using try catch statement'() {
        setup:
        def mockedLogger = Mock(Log)
        def saferInstance = getClassToTest(SafeImpl).newInstance(logger: mockedLogger)

        when:
        def result = saferInstance.add(x, y)

        then:
        result == z

        and:
        1 * mockedLogger.info("safe!!")

        where:
        x << [0, 1, 2, null]
        y << [null, 1, 2, 3]
        z << [100, 2, 4, 100]
    }

    @Unroll
    void 'make sure error is logged'() {
        setup:
        def mockedLogger = Mock(Log)
        def saferInstance = getClassToTest(SafeImpl).newInstance(logger: mockedLogger)

        when:
        def result = saferInstance.add(x, y)

        then:
        result == z

        and:
        1 * mockedLogger.error(_ as Exception)

        where:
        x << [0, null]
        y << [null, 1]
        z << [100, 100]
    }
}
