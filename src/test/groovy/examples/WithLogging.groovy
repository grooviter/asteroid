package examples

import asteroid.A
import asteroid.Local

@Local(WithLoggingTransformationImpl) // <1>
@interface WithLogging { }
