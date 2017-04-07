package asteroid.local.samples

class NameCheckerTest extends GroovyTestCase {

    void testCheckerSucceeds() {
        assertScript '''
        package asteroid.local.samples
        
        @NameChecker('.*Subject')
        class CheckerSubject {
        
          @NameChecker('.*Field')
          String stringField = 'doSomething'
        }
        
        assert new CheckerSubject().stringField == 'doSomething'
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
