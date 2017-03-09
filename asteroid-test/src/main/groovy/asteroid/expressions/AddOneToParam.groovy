package asteroid.expressions

import asteroid.Local

@Local(value = AddOneToParamImpl, applyTo = Local.TO.METHOD)
@interface AddOneToParam { }
