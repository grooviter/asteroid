package asteroid.local.samples

/**
 *
 */
class ExecuteMethodSafelyTest extends GroovyTestCase {

    void testSafeCallWorks() {
        assertScript '''
        package asteroid.local.samples

        class A {
             def value

             @ExecuteMethodSafely
             def execute() {

             }
        }

        A a = new A()

        assert a.execute() == null
        a.value = 1
        assert a.execute() == "1"

        '''
    }

}
