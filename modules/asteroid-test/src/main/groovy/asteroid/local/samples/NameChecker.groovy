package asteroid.local.samples

import asteroid.Local

@Local(value = NameCheckerImpl, applyTo = Local.TO.ANNOTATED)
@interface NameChecker {
    String value()
}
