package asteroid.global.samples

import groovy.transform.CompileStatic

import asteroid.AbstractGlobalTransformation
import asteroid.Phase
import asteroid.transformer.Transformer
import org.codehaus.groovy.control.CompilePhase

@CompileStatic
@Phase(CompilePhase.CONVERSION)
class ChangeEqualsTransformation extends AbstractGlobalTransformation {
    @Override
    List<Class<Transformer>> getTransformers() {
        return [ChangeEqualsTransformer] as List<Class<Transformer>>
    }
}
