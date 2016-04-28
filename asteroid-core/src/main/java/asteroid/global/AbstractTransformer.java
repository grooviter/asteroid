package asteroid.global;

import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.ClassCodeExpressionTransformer;

/**
 * Most transformers need at some point the source unit in order to
 * fix or apply properly the scope to each variable.
 *
 * This class enforces the use of a SourceUnit instance for every
 * transformer
 *
 * @since 0.2.0
 *
 */
public abstract class AbstractTransformer
    extends ClassCodeExpressionTransformer implements Transformer {

    private final SourceUnit sourceUnit;

    /**
     * This constructor needs a source unit
     *
     * @param sourceUnit the related source unit where the expression belongs
     * @since 0.2.0
     */
    public AbstractTransformer(final SourceUnit sourceUnit) {
        this.sourceUnit = sourceUnit;
    }

    /**
     * This method returns the source unit
     *
     * @return the source unit related to the expression we want to transform
     * @since 0.2.0
     */
    public SourceUnit getSourceUnit() {
        return this.sourceUnit;
    }

    /**
     * This method returns the module of the current
     * SourceUnit instance
     *
     * @return a ModuleNode instance
     * @since 0.2.0
     */
    public ModuleNode getModule() {
        return sourceUnit.getAST();
    }

    /**
     * Sometimes could be useful to get the package name
     * of the current module
     *
     * @return A String representing the current qualified package name
     * @since 0.2.0
     */
    public String getModulePackageName() {
        final ModuleNode module = getModule();

        if (module != null && module.getPackageName() != null) {
            return module.getPackageName();
        }

        return null;
    }
}
