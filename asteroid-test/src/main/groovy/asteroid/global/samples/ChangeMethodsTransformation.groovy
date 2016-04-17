package asteroid.global.samples

import groovy.transform.CompileStatic

import asteroid.A
import asteroid.global.Transformer
import asteroid.global.GlobalTransformation
import asteroid.global.GlobalTransformationImpl

/**
 * This {@link GlobalTransformationImpl} only applies a
 * transformer of type {@link ChangeTripleToPlusOne}
 *
 * @since 0.1.2
 */
@CompileStatic
@GlobalTransformation(A.PHASE_GLOBAL.SEMANTIC_ANALYSIS)
class ChangeMethodsTransformation extends GlobalTransformationImpl {

    @Override
    List<Class<Transformer>> getTransformers() {
        return [ChangeTripleXToPlusOne]
    }
}
