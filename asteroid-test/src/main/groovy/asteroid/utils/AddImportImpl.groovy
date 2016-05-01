package asteroid.utils

import static Phase.GLOBAL.CONVERSION

import asteroid.Phase
import asteroid.AbstractGlobalTransformation
import asteroid.transformer.Transformer

@Phase(CONVERSION)
class AddImportImpl extends AbstractGlobalTransformation {

    @Override
    List<Class<? extends Transformer>> getTransformers() {
        return [AddImportTransformer]
    }
}
