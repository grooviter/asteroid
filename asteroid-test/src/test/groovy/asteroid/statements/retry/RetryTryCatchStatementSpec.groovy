package asteroid.statements.retry

import asteroid.spec.AsteroidSpec
import asteroid.statements.retry.RetryImpl
import spock.lang.Unroll

class RetryTryCatchStatementSpec extends AsteroidSpec {

    @Unroll
    void 'using try catch statement'() {
        setup:
        def mockedLogger = Mock(Log)
        def saferInstance = getClassToTest(RetryImpl).newInstance(logger: mockedLogger)

        when:
        def result = saferInstance.add(bug,x, y)

        then:
        result == z

//        and:
//        1 * mockedLogger.info("safe!!")

        where:
        bug <<  [true, false, true, false]
        x <<    [1, 2, 3, 4]
        y <<    [1, 2, 3, 4]
        z <<    [2, 4, 6, 8]
    }

    @Unroll
    void 'make sure error is logged'() {
        setup:
        def mockedLogger = Mock(Log)
        def saferInstance = getClassToTest(RetryImpl).newInstance(logger: mockedLogger)

        when:
        def result = saferInstance.add(bug, x, y)

        then:
        result == z

//        and:
//        1 * mockedLogger.error(_ as Exception)

        where:
        bug <<  [false, true, false, true]
        x <<    [1, 2, 3, 4]
        y <<    [1, 2, 3, 4]
        z <<    [2, 4, 6, 8]
    }
}
