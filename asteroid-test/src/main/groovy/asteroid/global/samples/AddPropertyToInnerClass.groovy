package asteroid.global.samples

import groovy.transform.CompileStatic

import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.ClassNode

import asteroid.A
import asteroid.transformer.AbstractClassNodeTransformer

@CompileStatic
class AddPropertyToInnerClass extends AbstractClassNodeTransformer { // <1>

    AddPropertyToInnerClass(final SourceUnit sourceUnit) {
        super(sourceUnit,
              A.CRITERIA.byClassNodeNameContains('AddTransformerSpecExample$Input')) // <2>
    }

    @Override
    void transformClass(final ClassNode target) { // <3>
        A.UTIL.NODE.addInterfaces(target, java.io.Serializable)
    }
}
