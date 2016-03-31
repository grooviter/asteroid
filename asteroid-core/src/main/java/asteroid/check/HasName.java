package asteroid.check;

import asteroid.A;
import org.codehaus.groovy.ast.MethodNode;

/**
 * This {@link Checker} checks whether a given {@link MethodNode} has the expected
 * name of not.
 *
 * Normally it would be used when checking if a given {@link org.codehaus.groovy.ast.AnnotationNode} has been
 * applied to a specific method that has a specific name.
 *
 * @since 0.1.0
 */
public class HasName implements Checker<MethodNode> {
    private final String name;

    /**
     * Creates an instance of {@link HasName} with the name passed as argument
     *
     * @param name the name this checker will use to check the condition
     * @since 0.1.0
     */
    private HasName(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     *
     * @since 0.1.0
     */
    @Override
    public Result accepts(final MethodNode node) {
        return A.UTIL.MISC.createResult(node.getName().equals(this.name), node, getErrorMessage(node));
    }

    /**
     * Creates an instance of {@link HasName} with the name passed as argument
     *
     * @param name the name that will be used to check whether a method has the expected name or not.
     * @return an instance of {@link HasName}
     * @since 0.1.0
     */
    public static HasName hasName(String name) {
        return new HasName(name);
    }

    private String getErrorMessage(final MethodNode node) {
        return "Method [" + node.getName() + "] doesn't match the expected name [" + this.name + "]";
    }
}
