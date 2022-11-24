package asteroid.local.samples

import asteroid.Local

@Local(
    value   = WithLoggingTransformationImpl, // <1>
    applyTo = Local.TO.METHOD)               // <2>
@interface WithLogging { }
