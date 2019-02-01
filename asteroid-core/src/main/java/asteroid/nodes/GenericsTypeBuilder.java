package asteroid.nodes;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.GenericsType;

/**
 * Builder to create instances of type {@link GenericsType}
 *
 * @since 0.1.0
 */
final public class GenericsTypeBuilder {

    private final GenericsType genericsType;

    /**
     * Sets the type of the generic type
     *
     * @param classNode Sets the {@link ClassNode} of the generic type
     * @since 0.1.0
     */
    private GenericsTypeBuilder(final ClassNode classNode) {
        this.genericsType = new GenericsType(classNode);
    }

    /**
     * Sets the type of the generic type
     *
     * @param classNode Sets the {@link ClassNode} of the generic type
     * @return an instance of type {@link GenericsTypeBuilder}
     * @since 0.1.0
     */
    public static GenericsTypeBuilder generics(final ClassNode classNode) {
        return new GenericsTypeBuilder(classNode);
    }

    /**
     * Returns the configured generic type
     *
     * @return an instance of type {@link GenericsType}
     * @since 0.1.0
     */
    public GenericsType build() {
        return this.genericsType;
    }
}
