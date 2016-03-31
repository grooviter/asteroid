package asteroid.global.samples

import groovy.transform.CompileStatic

import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.ClassNode

import asteroid.A
import asteroid.global.ClassNodeTransformer

/**
 * This {@link ClassNodeTransformer} transforms classes containing
 * a certain string in its name.
 *
 * Once the transformation locates the {@link ClassNode} then it
 * makes the class to implement {@link java.io.Serializable}
 *
 * @since 0.1.2
 */
@CompileStatic
class AddPropertyToInnerClass extends ClassNodeTransformer { // <1>

    AddPropertyToInnerClass(final SourceUnit sourceUnit) {
        super(sourceUnit, byNameContains('AddTransformerSpecExample$Input')) // <2>
    }

    void transformClass(final ClassNode target) { // <3>
        A.UTIL.CLASS.addInterfaces(target, java.io.Serializable)
    }
}
