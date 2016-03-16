package examples

import static asteroid.A.TO

import asteroid.local.Local
import asteroid.local.Apply

@Apply(TO.FIELD)
@Local(ToMD5Impl)
@interface ToMD5 { }
