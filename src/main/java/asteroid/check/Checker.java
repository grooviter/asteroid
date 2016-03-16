package asteroid.check;

import org.codehaus.groovy.ast.ASTNode;

/**
 * An instance of Checker should test whether an instance of {@link ASTNode} passes
 * the assertion it represents or not.
 *
 * @param <T> The type of ASTNode under test
 * @since 0.1.0
 */
public interface Checker<T extends ASTNode> {

    /**
     * This function checks whether the node passed as parameter passes the assertion or not.
     *
     * @param node The node under test
     * @return an instance of {@link Result}
     * @since 0.1.0
     */
    Result accepts(T node);
}
