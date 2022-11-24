package asteroid.nodes;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.Parameter;

/**
 * Creates a new {@link Parameter} instance
 *
 * @since 0.1.0
 */
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public class ParameterNodeBuilder {

    private final String name;
    private ClassNode type;

    /**
     * Default constructor
     *
     * @param name of the parameter
     */
    public ParameterNodeBuilder(final String name) {
        this.name = name;
    }

    /**
     * Creates a new {@link ParameterNodeBuilder}
     *
     * @param name of the parameter
     * @return a new instance of {@link ParameterNodeBuilder}
     */
    public static ParameterNodeBuilder param(final String name) {
        return new ParameterNodeBuilder(name);
    }

    /**
     * Sets the type of the parameter
     *
     * @param type the {@link ClassNode} of the
     */
    public ParameterNodeBuilder type(final ClassNode type) {
        this.type = type;
        return this;
    }

    /**
     * Returns the {@link Parameter} instance
     *
     * @return an instance of {@link Parameter}
     */
    public Parameter build() {
        return new Parameter(type, name);
    }
}
