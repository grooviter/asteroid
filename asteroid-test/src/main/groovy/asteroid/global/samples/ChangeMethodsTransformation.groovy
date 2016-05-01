package asteroid.global.samples

import groovy.transform.CompileStatic

import asteroid.Phase
import asteroid.AbstractGlobalTransformation
import asteroid.transformer.Transformer

@CompileStatic
@Phase(Phase.GLOBAL.CANONICALIZATION)
class ChangeMethodsTransformation extends AbstractGlobalTransformation {

    @Override
    List<Class<Transformer>> getTransformers() {
        return [ChangeTripleXToPlusOne] as List<Class<Transformer>>
    }
}
