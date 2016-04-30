package asteroid.global.samples

import static asteroid.Phase.GLOBAL

import groovy.transform.CompileStatic

import asteroid.Phase
import asteroid.AbstractGlobalTransformation
import asteroid.transformer.Transformer

/**
 * This {@link AbstractGlobalTransformation} only applies a
 * transformer of type {@link AddPropertyToInnerClass}
 *
 * @since 0.1.2
 */
@CompileStatic
@Phase(GLOBAL.CONVERSION) // <1>
class AddTransformation extends AbstractGlobalTransformation { // <2>

    List<Class<Transformer>> getTransformers() {
        return [AddPropertyToInnerClass, AddTraitTransformer] // <3>
    }
}
