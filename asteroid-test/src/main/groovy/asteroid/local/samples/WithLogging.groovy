package asteroid.local.samples

import static asteroid.A.TO

import asteroid.Local
import asteroid.Apply

@Apply(TO.METHOD) // <1>
@Local(WithLoggingTransformationImpl) // <2>
@interface WithLogging { }
