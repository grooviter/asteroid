package asteroid

import asteroid.A.TO
import asteroid.local.Local
import asteroid.local.Apply

/**
 * When applied to a method this annotation will trigger a
 * AST transformation that will make the annotated method to
 * return the message declared in this annotation.
 */
@Apply(TO.METHOD)
@Local(MessengerTransformationImpl)
@interface Messenger {
    /**
     * The message you want to be returned by annotated method
     */
    String value()
}
