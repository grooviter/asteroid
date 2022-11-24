package asteroid.nodes;

import asteroid.A;
import org.codehaus.groovy.syntax.Types;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.stmt.Statement;

/**
 * Builder to create instance of type {@link MethodNode}
 *
 * @since 0.1.0
 */
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
final public class MethodNodeBuilder {

    private final String name;
    private int modifiers;
    private Statement code;
    private ClassNode returnType;
    private Parameter[] parameters = new Parameter[0];
    private ClassNode[] exceptions = new ClassNode[0];

    private MethodNodeBuilder(final String name) {
        this.name = name;
    }

    /**
     * Sets the method name and creates an instance of {@link
     * MethodNodeBuilder}
     *
     * @param name the name of the method
     * @return the current builder instance
     * @since 0.1.0
     */
    public static MethodNodeBuilder method(final String name) {
        return new MethodNodeBuilder(name);
    }

    /**
     * Sets the return type of the method
     *
     * @param returnType a {@link Class} representing the return type
     * of the method
     * @return the current builder instance
     * @since 0.1.0
     */
    public MethodNodeBuilder returnType(final Class returnType) {
        this.returnType = A.NODES.clazz(returnType).build();
        return this;
    }

    /**
     * Sets the method modifiers (public, protected...). You can use
     * {@link Types} to find proper modifier values.
     *
     * @param modifiers an int representing the type of the modifiers
     * @return the current builder instance
     * @see Types
     * @since 0.1.0
     */
    public MethodNodeBuilder modifiers(final int modifiers) {
        this.modifiers = modifiers;
        return this;
    }

    /**
     * Declares which parameters this method may receive
     *
     * @param parameters an array of instances of type {@link Parameter}
     * @return the current builder instance
     * @since 0.1.0
     */
    public MethodNodeBuilder parameters(final Parameter... parameters) {
        this.parameters = parameters;
        return this;
    }

    /**
     * Declares which type of exceptions this method may throw
     *
     * @param exceptions an array of {@link ClassNode} representing
     * the different type of exceptions this method may throw
     * @return the current builder instance
     * @since 0.1.0
     */
    public MethodNodeBuilder exceptions(final ClassNode... exceptions) {
        this.exceptions = exceptions;
        return this;
    }

    /**
     * Sets the content of the method
     *
     * @param code the method's code. It has to be of type {@link Statement}
     * @return the current builder instance
     * @since 0.1.0
     */
    public MethodNodeBuilder code(final Statement code) {
        this.code = code;
        return this;
    }

    /**
     * Returns the configured instance
     *
     * @return an instance of type {@link MethodNode}
     * @since 0.1.0
     */
    public MethodNode build() {
        final MethodNode methodNode =
            new MethodNode(name,
                           modifiers,
                           returnType,
                           parameters,
                           exceptions,
                           code);

        return methodNode;
    }

}
