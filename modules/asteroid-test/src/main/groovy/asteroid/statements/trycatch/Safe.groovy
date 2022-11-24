package asteroid.statements.trycatch

import asteroid.Local

@Local(value = SafeImpl, applyTo = Local.TO.METHOD)
@interface Safe {
    int value()
}
