package asteroid.statements.retry

import asteroid.statements.trycatch.Safe

class RetryTryCatchStatementSpecExample {

    @Retry(1)
    Integer add(boolean bug, Integer one, Integer two) {
//        if (bug) {
//            throw Exception()
//        } else {
            return one + two
//        }
    }
}
