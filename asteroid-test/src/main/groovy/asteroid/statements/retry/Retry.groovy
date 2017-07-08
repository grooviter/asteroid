package asteroid.statements.retry

import asteroid.Local
import asteroid.statements.retry.RetryImpl

@Local(value = RetryImpl, applyTo = Local.TO.METHOD)
@interface Retry {
    int value()
}
