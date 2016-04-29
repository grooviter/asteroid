package asteroid;

import org.codehaus.groovy.transform.GroovyASTTransformationClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a given annotation as a local AST transformation
 * <br>
 *
 * <pre><code>
 * {@literal @}Local(MyTransformationImplementation.class)
 *  public {@literal @}interface MyAnnotation {
 *     //...
 *  }
 * </code></pre>
 * Then you can use your annotation:
 * <pre><code>
 * {@literal @}MyAnnotation
 *  class SomeType {
 *     //...
 *  }
 * </code></pre>
 *
 * @since 0.1.0
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.SOURCE)
@GroovyASTTransformationClass("asteroid.internal.LocalTransformation")
public @interface Local {
    /**
     * Sets the transformation implementation class
     *
     * @return a class representing an AST transformation implementation
     * @since 0.1.0
     */
    Class<? extends AbstractLocalTransformation> value();
}
