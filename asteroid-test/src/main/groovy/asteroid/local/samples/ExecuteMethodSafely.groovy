package asteroid.local.samples

import asteroid.A
import asteroid.Local

@Local(value = ExecuteMethodSafelyImpl, applyTo = A.TO.METHOD)
@interface ExecuteMethodSafely {}
