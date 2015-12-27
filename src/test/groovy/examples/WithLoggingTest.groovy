package examples

/**
 *
 */
class WithLoggingTest extends GroovyTestCase {

    void testLoggingWorks() {
        assertScript '''
            package examples

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
