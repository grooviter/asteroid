package asteroid.local.samples

/**
 *
 */
class SerializableTest extends GroovyTestCase {

    void testWorks() {
        assertScript '''
        package asteroid.local.samples

        @Serializable
        class A { }

        A something = new A()

        assert something instanceof java.io.Serializable
        assert something instanceof Cloneable

        '''
    }

    // tag::checkersShouldFail[]
    void testFailsBecauseNumberOfMethods() {
        shouldFail '''
        package asteroid.local.samples

        @Serializable
        class A {
            def a() {}
            def b() {}
        }
        '''
    }
    // end::checkersShouldFail[]

    void testFailsBecausePackage() {
        shouldFail '''
        package asteroidx.local.samples

        @Serializable
        class A {
            def a() {}
        }
        '''
    }
}
