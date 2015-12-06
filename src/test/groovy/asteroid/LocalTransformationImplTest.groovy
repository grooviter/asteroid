package asteroid

class LocalTransformationImplTest extends GroovyTestCase {

    /**
     * Tests that the {@link LocalTransformationImpl} works
     */
    void testCheckAnnotatedNode() {
        assertScript '''
           import asteroid.Messenger

           @Messenger("message")
           String saySomething() {
              // provided by the annotation
           }

           assert saySomething() == "message"
        '''
    }

}
