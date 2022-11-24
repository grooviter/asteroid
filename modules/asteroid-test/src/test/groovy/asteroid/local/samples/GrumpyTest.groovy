package asteroid.local.samples

/**
 *
 */
class GrumpyTest extends GroovyTestCase {

    void testAddErrorWorks() {
        shouldFail '''
        package asteroid.local.samples

        @Grumpy
        class A {
           String name
        }
        '''
    }

    void testAddErrorMessages() {
        try {
            assertScript '''
            package asteroid.local.samples

            @Grumpy
            class A {
               String name
            }
            '''
        } catch(Exception ex) {
            assert ex.message.contains("said the Grumpy transformation")
        }
    }
}
