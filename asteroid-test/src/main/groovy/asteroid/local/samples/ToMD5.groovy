package asteroid.local.samples

import static asteroid.A.TO

import asteroid.Local
import asteroid.Apply

@Apply(TO.FIELD)
@Local(ToMD5Impl)
@interface ToMD5 { }
