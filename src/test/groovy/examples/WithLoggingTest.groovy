package examples

/**
 *
 */
class WithLoggingTest extends GroovyTestCase {

    void testLoggingWorks() {
        assertScript '''
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
