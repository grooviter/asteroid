package asteroid.global.samples

import groovy.transform.CompileStatic

import asteroid.A
import asteroid.global.Transformer
import asteroid.global.GlobalTransformation
import asteroid.global.GlobalTransformationImpl

/**
 * This {@link GlobalTransformationImpl} only applies a
 * transformer of type {@link ChangeTripleXToPlusOne}
 *
 * @since 0.1.2
 */
@CompileStatic
@GlobalTransformation(A.PHASE_GLOBAL.CANONICALIZATION)
class ChangeMethodsTransformation extends GlobalTransformationImpl {

    @Override
    List<Class<Transformer>> getTransformers() {
        return [ChangeTripleXToPlusOne] as List<Class<Transformer>>
    }
}
