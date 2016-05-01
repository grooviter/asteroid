package asteroid.transformer;

import asteroid.AbstractGlobalTransformation;
import org.codehaus.groovy.ast.ClassNode;

/**
 * All transformer implementations responsible for modifying an AST
 * node in a {@link AbstractGlobalTransformation} should implement
 * this interface
 *
 * @since 0.2.0
 */
public interface Transformer {

    /**
     * This function has to be implemented in order the transformation
     * to traverse every {@link ClassNode} in the AST
     *
     * @param clazzNode The next {@link ClassNode} to traverse
     * @since 0.2.0
     */
    void visitClass(ClassNode clazzNode);
}
