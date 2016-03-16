package examples


/**
 *
 */
class SerializableTest extends GroovyTestCase {

    void testSerializableWorks() {
        assertScript '''
        package examples

        @Serializable
        class A { }

        A something = new A()

        assert something instanceof java.io.Serializable
        assert something instanceof Cloneable

        '''
    }

}
