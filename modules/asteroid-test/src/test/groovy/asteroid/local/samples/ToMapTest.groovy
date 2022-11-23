package asteroid.local.samples

/**
 *
 */
class ToMapTest extends GroovyTestCase {

    void testToMapWorks() {
        assertScript '''
        package asteroid.local.samples

        @ToMap
        class A {
           String name
           Integer age
        }

        A sample = new A(name: "john", age: 22)

        assert sample.toMap().name == "john"
        assert sample.toMap().age  == 22

        '''
    }
}
