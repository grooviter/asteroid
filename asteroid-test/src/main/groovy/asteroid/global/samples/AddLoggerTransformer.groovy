package asteroid.global.samples

import asteroid.A
import asteroid.transformer.AbstractClassNodeTransformer

import groovy.util.logging.Log
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.classgen.VariableScopeVisitor

// tag::complexCriteria[]
class AddLoggerTransformer extends AbstractClassNodeTransformer {

    static final Closure<Boolean> CRITERIA = // <1>
        A.CRITERIA.with {
            and(byClassNodeNameStartsWith('asteroid.global.samples'),
                or(byClassNodeNameContains('Logger'),
                   byClassNodeNameEndsWith('Example')))
        }

    AddLoggerTransformer(final SourceUnit sourceUnit) {
        super(sourceUnit, CRITERIA) // <2>
    }

    @Override
    void transformClass(final ClassNode target) { // <3>
        target.addAnnotation(A.NODES.annotation(Log).build())
    }
}
// end::complexCriteria[]
