package asteroid.local;

import org.codehaus.groovy.transform.GroovyASTTransformationClass;

import asteroid.A;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Can be used when declaring an implementation of {@link LocalTransformationImpl} to reduce
 * the boilerplate code needed:
 * <br><br>t
 * <ul>
 *     <li>No need to implement constructor ()</li>
 *     <li>No need to provide {@link org.codehaus.groovy.transform.GroovyASTTransformation}</li>
 *     <li>Limits the compilation phases to avoid using a non allowed phase</li>
 * </ul>
 *
 * @since 0.1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@GroovyASTTransformationClass("asteroid.internal.LocalTransformationTransformation")
public @interface LocalTransformation {
    /**
     * Sets the local phase compilation
     *
     * @return the specific {@link asteroid.A.PHASE_LOCAL} when transformation will be applied
     * @since 0.1.0
     */
    A.PHASE_LOCAL value();
}
