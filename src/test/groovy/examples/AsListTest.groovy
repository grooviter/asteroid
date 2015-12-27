package examples


/**
 *
 */
class AsListTest extends GroovyTestCase {

    void testAsListWorks() {
        assertScript '''
        package examples

        @AsList
        class A { }

        A list = new A()

        list.add(1)

        assert list.size() == 1

        '''
    }

}
