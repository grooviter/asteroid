package asteroid.utils

import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.ast.ClassNode

import asteroid.A
import asteroid.transformer.AbstractClassNodeTransformer

/**
 * Using a {@link AbstractClassNodeTransformer} to add imports
 *
 * @since 0.1.6
 */
// tag::classnodetransformer[]
class AddImportTransformer extends AbstractClassNodeTransformer { // <1>

    public AddImportTransformer(final SourceUnit sourceUnit) {
        super(sourceUnit,
              A.CRITERIA.byAnnotationSimpleName('AddImport')) // <2>

    }

    /**
     * {@inheritDocs}
     */
    @Override
    void transformClass(final ClassNode target) { // <3>
        A.UTIL.NODE.addImport(target, groovy.json.JsonOutput) // <4>
    }
}
// end::classnodetransformer[]
