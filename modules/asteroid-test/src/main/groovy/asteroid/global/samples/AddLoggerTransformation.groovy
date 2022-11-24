package asteroid.global.samples

import groovy.transform.CompileStatic

import asteroid.Phase
import asteroid.AbstractGlobalTransformation
import asteroid.transformer.Transformer

@CompileStatic
@Phase(Phase.GLOBAL.CONVERSION)
class AddLoggerTransformation extends AbstractGlobalTransformation {

    @Override
    List<Class<Transformer>> getTransformers() {
        return [AddLoggerTransformer] as List<Class<Transformer>>
    }
}
