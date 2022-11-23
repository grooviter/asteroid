package asteroid.local.samples

import asteroid.Local

@Local(value = ToMD5Impl, applyTo = Local.TO.FIELD)
@interface ToMD5 { }
