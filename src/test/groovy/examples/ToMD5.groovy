package examples

import static asteroid.A.TO

import asteroid.Apply
import asteroid.Local

@Apply(TO.FIELD)
@Local(ToMD5Impl)
@interface ToMD5 { }
