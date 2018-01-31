package asteroid.statements.retry

import asteroid.spec.AsteroidSpec
import asteroid.statements.retry.RetryImpl
import spock.lang.Unroll
import java.util.logging.Logger

class RetryTryCatchStatementSpec extends AsteroidSpec {

    @Unroll
    void 'using try catch statement'() {
        setup:
        def mockedLogger = Mock(Logger)
        def saferInstance = getClassToTest(RetryImpl).newInstance(logger: mockedLogger)

        when:
        def result = saferInstance.add(bug,x, y)

        then:
        result == z

        and:
        (1..2) * mockedLogger.info(_)

        where:
        bug <<  [true, false, true, false]
        x <<    [1, 2, 3, 4]
        y <<    [1, 2, 3, 4]
        z <<    [null, 4, null, 8]
    }

    @Unroll
    void 'make sure error is logged'() {
        setup:
        def mockedLogger = Mock(Logger)
        def saferInstance = getClassToTest(RetryImpl).newInstance(logger: mockedLogger)

        when:
        def result = saferInstance.add(bug, x, y)

        then:
        result == z

        and:
        (1..2) * mockedLogger.info(_)

        where:
        bug <<  [false, true, false, true]
        x <<    [1, 2, 3, 4]
        y <<    [1, 2, 3, 4]
        z <<    [2, null, 6, null]
    }

    void 'check nasty behavior'() {
        setup: 'create instance with mocked logger'
        def mockedLogger = Mock(Logger)
        def saferInstance = getClassToTest(RetryImpl).newInstance(logger: mockedLogger)

        when: 'executing method'
        def result = saferInstance.doSomethingNasty()

        then: 'eventually it should return 42'
        result == 42

        and: 'also have logged some executions'
        3 * mockedLogger.info(_)
        2 * mockedLogger.severe(_)
    }
}
