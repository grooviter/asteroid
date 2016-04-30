package asteroid.local.samples

import asteroid.A
import asteroid.Local

@Local(value = ToMD5Impl, applyTo = A.TO.FIELD)
@interface ToMD5 { }
