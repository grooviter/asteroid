package asteroid.utils;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.every;

import groovy.lang.Closure;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.first;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.last;

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
     * Combines two {@link Closure} expressions returning a boolean.
     * The result will be a function that returns true if the
     * parameter passed makes any of the former functions to return
     * true.
     *
     * <strong>AST</strong>
     * <pre><code>def even     = { x -> x % 2 == 0 }
     *def positive = { y -> y > 0 }
     *
     *def evenOrPositive = or(even, positive)</code></pre>
     *
     * @param fns functions to combine
     * @return a combined {@link Closure}
     * @since 0.2.3
     */
    public Closure<Boolean> or(final Closure<Boolean>... fns) {
        return new Closure(null) {
            public boolean doCall(final Object o) {
                return any(fns,
                           new Closure(null) {
                               public boolean doCall(final Closure<Boolean> fn) {
                                   return fn.call(o);
                               }
                           });
            }
        };
    }

    /**
     * Combines two {@link Closure} expressions returning a boolean.
     * The result will be a function that returns true only if the
     * parameter passed makes all of the former functions to return
     * true.
     *
     * <strong>AST</strong>
     * <pre><code>def even     = { x -> x % 2 == 0 }
     *def positive = { y -> y > 0 }
     *
     *def evenAndPositive = and(even, positive)</code></pre>
     *
     * @param fns functions to combine
     * @return a combined {@link Closure}
     * @since 0.2.3
     */
    public Closure<Boolean> and(final Closure<Boolean>... fns) {
        return new Closure(null) {
            public boolean doCall(final Object o) {
                return every(fns,
                             new Closure(null) {
                                 public boolean doCall(final Closure<Boolean> fn) {
                                     return fn.call(o);
                                 }
                             });
            }
        };
    }
}
