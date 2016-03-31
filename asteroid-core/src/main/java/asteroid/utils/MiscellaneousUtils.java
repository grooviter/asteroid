package asteroid.utils;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.first;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.last;

import asteroid.check.Result;
import org.codehaus.groovy.ast.ASTNode;

/**
 * General utility methods to deal with {@link ASTNode} instances
 *
 * @since 0.1.4
 * @see asteroid.Utils
 */
public final class MiscellaneousUtils {

    /**
     * Returns the first node of the list passed as parameter with the expected type T
     *
     * @param nodes a list of nodes
     * @param clazz the type of the expected value
     * @param <T> the expected type
     * @return a node with type T
     * @since 0.1.4
     */
    public <T> T getFirstNodeAs(final ASTNode[] nodes, final Class<T> clazz) {
        return (T) first(nodes);
    }

    /**
     * Returns the last node of the list passed as parameter with the expected type T
     *
     * @param nodes a list of nodes
     * @param clazz the type of the expected value
     * @param <T> the expected type
     * @return a node with type T
     * @since 0.1.4
     */
    public <T> T getLastNodeAs(final ASTNode[] nodes, final Class<T> clazz) {
        return (T) last(nodes);
    }

    /**
     * When executing a checker the result will be wrapped in a {@link Result} instance. This
     * method ease the way of creating a {@link Result} instance.
     *
     * @param passes whether the result is successful or not
     * @param node the node under test
     * @param errorMessage The message in case result was not successful
     * @since 0.1.4
     */
    public Result createResult(Boolean passes, ASTNode node, String errorMessage) {
        return new Result(node, passes ? Result.Status.PASSED : Result.Status.ERROR, errorMessage);
    }
}
