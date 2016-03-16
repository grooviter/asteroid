package asteroid;

import asteroid.check.ChecksBuilder;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import org.codehaus.groovy.control.SourceUnit;

/**
 * This class can be seen as a proxy to instances of {@link ChecksBuilder}
 *
 * @since 0.1.0
 */
public final class Checks {

    /**
     * See {@link ChecksBuilder#source(SourceUnit, Closure)}
     *
     * @param sourceUnit see {@link ChecksBuilder#source(SourceUnit, Closure)}
     * @param closure see {@link ChecksBuilder#source(SourceUnit, Closure)}
     * @since 0.1.0
     */
    public void source(SourceUnit sourceUnit, @DelegatesTo(ChecksBuilder.class) Closure<?> closure) {
        ChecksBuilder.source(sourceUnit, closure);
    }

    /**
     *
     * See see {@link ChecksBuilder#source(SourceUnit)}
     *
     * @param sourceUnit see {@link ChecksBuilder#source(SourceUnit)}
     * @since 0.1.0
     */
    public ChecksBuilder source(SourceUnit sourceUnit){
        return ChecksBuilder.source(sourceUnit);
    }

}
