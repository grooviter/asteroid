package asteroid.global;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.codehaus.groovy.control.CompilePhase;

/**
 * Can be used when declaring an implementation of {@link GlobalTransformationImpl} to reduce
 * the boilerplate code needed:
 * <br><br>
 * <ul>
 *     <li>No need to provide {@link org.codehaus.groovy.transform.GroovyASTTransformation}</li>
 *     <li>Limits the compilation phases to avoid using a non allowed phase</li>
 * </ul>
 *
 * @since 0.1.2
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface GlobalTransformation {
    /**
     * Sets the global phase compilation
     *
     * @return the specific {@link asteroid.A.PHASE_GLOBAL} when transformation will be applied
     * @since 0.1.2
     * @see CompilePhase
     */
    CompilePhase value();
}
