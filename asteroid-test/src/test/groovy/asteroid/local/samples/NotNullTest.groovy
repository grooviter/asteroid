package asteroid.local.samples

class NotNullTest extends GroovyTestCase {

    void testNotNullFails() {
        shouldFail '''
        package asteroid.local.samples
        class A {
          String value
          
          @NotNull
          A(String value) {
            this.value = value
          }
        }
        assert new A().value
        '''
    }

    void testNotNullSucceeds() {
        assertScript '''
        package asteroid.local.samples
        class A {
          String value
          
          @NotNull
          A(String value) {
            this.value = value
          }            
        }
        
         assert new A('something').value        
        '''
    }
}
