package asteroid.local.samples

/**
 *
 */
class WithLoggingTest extends GroovyTestCase {

    void testLoggingWorks() {
        assertScript '''
        package asteroid.local.samples

            // tag::testCode[]
            @WithLogging
            def greet() {
                println "Hello World"
            }

            greet()
            // end::testCode[]
        '''
    }
}
