package asteroid.utils

import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.ast.ClassNode

import asteroid.A
import asteroid.global.ClassNodeTransformer

/**
 * Using a {@link ClassNodeTransformer} to add imports
 *
 * @since 0.1.6
 */
class AddImportTransformer extends ClassNodeTransformer {

    public AddImportTransformer(final SourceUnit sourceUnit) {
        super(sourceUnit, byAnnotationName(AddImport.simpleName))
    }

    /**
     * {@inheritDocs}
     */
    @Override
    void transformClass(final ClassNode target) {
        A.UTIL.CLASS.addImport(target, groovy.json.JsonOutput)
    }
}
