package asteroid.local.samples.criterias

class CheckImportantMethodsTest extends GroovyTestCase {

    void testCheckImportantMethodsWorks() {
        assertScript '''
        package asteroid.local.samples.criterias

        @CheckImportantMethods
        class CheckImportantMethodsExample {

            @Important
            String getSomethingUseless() {
                return "something"
            }

            @Important
            String getAnything() {
                return "anything"
            }

            @Important
            String findSomethingImportant() {
                return "important stuff"
            }
        }

        assert true
        '''

        // Checking analysis is correct
        String userHome = System.getProperty('user.home')
        File analysisFile = new File(userHome, 'checkimportantmethodsexample.txt')
        List<String> methods = analysisFile.readLines()

        assert 'getSomethingUseless' in methods
        assert 'getAnything' in methods

        analysisFile.delete()
    }
}
