package asteroid.local.samples

import asteroid.Local

@Local(value = ExecuteMethodSafelyImpl, applyTo = Local.TO.METHOD)
@interface ExecuteMethodSafely {}
