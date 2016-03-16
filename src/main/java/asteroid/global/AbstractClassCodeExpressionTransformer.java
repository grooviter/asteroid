package asteroid.global;

import groovyjarjarasm.asm.Opcodes;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.ClassCodeExpressionTransformer;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.syntax.SyntaxException;

/**
 * Most transformers need at some point the source unit in order to
 * fix or apply properly the scope to each variable.
 *
 * This class enforces the use of a SourceUnit instance for every
 * transformer
 *
 * @since 0.1.1
 *
 */
abstract class AbstractClassCodeExpressionTransformer extends ClassCodeExpressionTransformer {

    private SourceUnit sourceUnit;

    /**
     * This constructor needs a source unit
     *
     * @param sourceUnit the related source unit where the expression belongs
     * @since 0.1.1
     */
    public AbstractClassCodeExpressionTransformer(final SourceUnit sourceUnit) {
        this.sourceUnit = sourceUnit;
    }

    /**
     * This method returns the source unit
     *
     * @return the source unit related to the expression we want to transform
     * @since 0.1.1
     */
    public SourceUnit getSourceUnit() {
        return this.sourceUnit;
    }

    /**
     * This method creates a new SyntaxException
     *
     * @param node The node causing the exception
     * @param message A meaningful exception message to the user
     * @since 0.1.1
     */
    public void addError(final ASTNode node, final String message) {
        sourceUnit.addError(
            new SyntaxException(
                message,
                node.getColumnNumber(),
                node.getLineNumber()));
    }

    /**
     * This method returns the module of the current
     * SourceUnit instance
     *
     * @return a ModuleNode instance
     * @since 0.1.1
     */
    public ModuleNode getModule() {
        return sourceUnit.getAST();
    }

    /**
     * Sometimes could be useful to get the package name
     * of the current module
     *
     * @return A String representing the current qualified package name
     * @since 0.1.1
     */
    public String getModulePackageName() {
        ModuleNode module = getModule();

        return module != null && module.getPackageName() != null ? module.getPackageName() : "";
    }

    /**
     * Classes implementing this method should provide a way
     * of knowing whether the expression should be transform
     * or not
     *
     * @param expression the expression we may want to transform
     * @return true if the current expression should be transformed or
     * false otherwise
     * @since 0.1.1
     */
    public abstract boolean isTransformable(Expression expression);

}
