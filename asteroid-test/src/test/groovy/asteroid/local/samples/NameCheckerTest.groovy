package asteroid.local.samples

class NameCheckerTest extends GroovyTestCase {

    void testCheckerSucceeds() {
        assertScript '''
        // tag::checkerSucceeds[]
        package asteroid.local.samples
        
        @NameChecker('.*Subject')
        class CheckerSubject {
        
          @NameChecker('.*Field')
          String stringField = 'doSomething'
        }
        
        assert new CheckerSubject().stringField == 'doSomething'
        // end::checkerSucceeds[]
        '''
    }

    void testCheckerFails() {
        shouldFail '''
        package asteroid.local.samples
        
        @NameChecker('.*NonSubject')
        class CheckerSubject { 
        
        }
        
        assert new CheckerSubject()
        '''
    }

    void testCheckerFailsWrongTarget() {
        shouldFail '''
        @NameChecker('.*NonSubject')
        package asteroid.local.samples
        
        class CheckerSubject { 
        
        }
        
        assert new CheckerSubject()
        '''
    }
}
