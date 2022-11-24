package asteroid.statements.retry

class RetryTryCatchStatementSpecExample {

    Integer myCounter = 0

    @Retry(10)
    Integer doSomethingNasty() {
        if (myCounter >= 2) {
            return 42
        }
        myCounter++
        throw new Exception("not yet little Jedi")
    }

    @Retry(1)
    Integer add(boolean bug, Integer one, Integer two) {
        if (bug) {
            throw new Exception()
        } else {
            return one + two
        }
    }
}
