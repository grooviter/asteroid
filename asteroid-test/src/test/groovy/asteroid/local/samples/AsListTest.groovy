package asteroid.local.samples

/**
 *
 */
class AsListTest extends GroovyTestCase {

    void testAsListWorks() {
        assertScript '''
        package asteroid.local.samples

        @AsList
        class A { }

        A list = new A()

        list.add(1)

        assert list.size() == 1

        '''
    }

}
