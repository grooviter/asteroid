package examples

/**
 *
 */
class ToMD5Test extends GroovyTestCase {

    void testToMD5Works() {
        assertScript '''
            package examples

            class A {
               @ToMD5 String name
            }

            A sample = new A(name: "john")

            assert sample.nameToMD5() == "527bd5b5d689e2c32ae974c6229ff785"
        '''
    }
}
