package asteroid.statements.retry

import asteroid.Local

@Local(value = RetryImpl, applyTo = Local.TO.METHOD)
@interface Retry {
    int value()
}
