package asteroid.utils;

import org.codehaus.groovy.runtime.DefaultGroovyMethods;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import groovy.lang.Closure;

/**
 * Internal utils to deal with Java code
 *
 * @since 0.4.3
 */
public final class InternalUtils {

    private InternalUtils() {
        // can't be instantiated
    }

    /**
     * Deals with deprecation of some form of collect, and possible
     * NPE
     *
     * @param iterable the object we'd like to iterate over
     * @param mapping {@link Closure} to transform items in the iterable object
     * @return an instance of type {@link List} with transformed items
     * @since 0.4.3
     */
    public static <T> List<T> collect(final Object iterable, final Closure<T> process) {
        if (iterable == null) {
            return new ArrayList<>();
        }

        return DefaultGroovyMethods.collect(iterable, process);
    }

    /**
     * Deals with deprecation of some form of collect, and possible
     * NPE
     *
     * @param iterable the object we'd like to iterate over
     * @return an instance of type {@link List}
     * @since 0.4.3
     */
    public static <T> List<T> collect(final Collection<T> iterable) {
        if (iterable == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(DefaultGroovyMethods.collect(iterable.iterator()));
    }
}
