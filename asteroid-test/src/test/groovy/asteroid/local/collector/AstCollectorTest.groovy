package asteroid.local.collector

class AstCollectorTest extends GroovyTestCase {

    void testCollectorWorksWithAsteroidLocal() {
        assertScript '''
        package asteroid.local.collector

        @ToEverything
        class A {
            String name
        }

        A aInstance = new A()

        assert aInstance.toJson()
        assert aInstance.toString()
        '''
    }
}
