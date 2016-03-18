package asteroid.local.samples

import asteroid.A
import asteroid.local.Local
import asteroid.local.Apply

@Apply(A.TO.METHOD)
@Local(ExecuteMethodSafelyImpl)
@interface ExecuteMethodSafely {}
