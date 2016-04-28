package asteroid.global.samples

import groovy.transform.CompileStatic

import asteroid.A
import asteroid.global.Transformer
import asteroid.global.GlobalTransformation
import asteroid.global.GlobalTransformationImpl

/**
 * This {@link GlobalTransformationImpl} only applies a
 * transformer of type {@link AddPropertyToInnerClass}
 *
 * @since 0.1.2
 */
@CompileStatic
@GlobalTransformation(A.PHASE_GLOBAL.CONVERSION) // <1>
class AddTransformation extends GlobalTransformationImpl { // <2>

    List<Class<Transformer>> getTransformers() {
        return [AddPropertyToInnerClass, AddTraitTransformer] // <3>
    }
}
