package asteroid.local.samples

import asteroid.A
import asteroid.Local
import asteroid.Apply

@Apply(A.TO.METHOD)
@Local(ExecuteMethodSafelyImpl)
@interface ExecuteMethodSafely {}
