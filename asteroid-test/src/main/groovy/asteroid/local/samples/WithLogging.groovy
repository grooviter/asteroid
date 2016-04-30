package asteroid.local.samples

import static asteroid.A.TO
import asteroid.Local

@Local(value = WithLoggingTransformationImpl, applyTo = TO.METHOD) // <2>
@interface WithLogging { }
