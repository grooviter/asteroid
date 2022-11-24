package asteroid.local.samples

import asteroid.Local

@Local(value = NotNullImpl, applyTo = Local.TO.CONSTRUCTOR)
@interface NotNull {

}
