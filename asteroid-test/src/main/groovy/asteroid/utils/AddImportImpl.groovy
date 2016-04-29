package asteroid.utils

import java.util.List
import java.util.ArrayList

import asteroid.A
import asteroid.GlobalTransformation
import asteroid.AbstractGlobalTransformation
import asteroid.transformer.Transformer

@GlobalTransformation(A.PHASE_GLOBAL.CONVERSION)
class AddImportImpl extends AbstractGlobalTransformation {

    @Override
    List<Class<? extends Transformer>> getTransformers() {
        return [AddImportTransformer]
    }
}
