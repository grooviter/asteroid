package asteroid.local.samples

/**
 * Tests methods related to add nodes to a class node
 *
 * @since 0.1.4
 */
class AddElementsTest extends GroovyTestCase {

    void testAddElementsWorks() {
        assertScript '''
        package asteroid.local.samples

        @AddElements
        class A { }

        A instance = new A()

        assert instance.solution       == "42"
        assert instance.getSimpleTip() == "No tips for you pal :P"
        '''
    }
}
