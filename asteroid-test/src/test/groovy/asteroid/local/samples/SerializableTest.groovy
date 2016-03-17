package asteroid.local.samples

/**
 *
 */
class SerializableTest extends GroovyTestCase {

    void testSerializableWorks() {
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
    void testSerializableCheckersWork() {
        shouldFail '''
        package asteroid.local.samples

        @Serializable
        class A {
            def a() {}
            def b() {}
        }

        A something = new A()

        assert something instanceof java.io.Serializable
        assert something instanceof Cloneable

        '''
    }
    // end::checkersShouldFail[]

}
