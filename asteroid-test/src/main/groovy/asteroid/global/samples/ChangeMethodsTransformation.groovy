package asteroid.global.samples

import groovy.transform.CompileStatic

import asteroid.A
import asteroid.GlobalTransformation
import asteroid.AbstractGlobalTransformation
import asteroid.transformer.Transformer

/**
 * This {@link AbstractGlobalTransformation} only applies a
 * transformer of type {@link ChangeTripleXToPlusOne}
 *
 * @since 0.1.2
 */
@CompileStatic
@GlobalTransformation(A.PHASE_GLOBAL.CANONICALIZATION)
class ChangeMethodsTransformation extends AbstractGlobalTransformation {

    @Override
    List<Class<Transformer>> getTransformers() {
        return [ChangeTripleXToPlusOne] as List<Class<Transformer>>
    }
}
