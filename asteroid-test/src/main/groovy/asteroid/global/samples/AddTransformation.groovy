package asteroid.global.samples

import static asteroid.Phase.GLOBAL

import groovy.transform.CompileStatic

import asteroid.Phase
import asteroid.AbstractGlobalTransformation
import asteroid.transformer.Transformer

@CompileStatic
@Phase(GLOBAL.CONVERSION) // <1>
class AddTransformation extends AbstractGlobalTransformation { // <2>

    @Override
    List<Class<Transformer>> getTransformers() {
        return [AddPropertyToInnerClass, AddTraitTransformer] // <3>
    }
}
