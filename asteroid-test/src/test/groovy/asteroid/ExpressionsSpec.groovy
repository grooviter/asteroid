package asteroid

import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MapEntryExpression
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.macro.matcher.ASTMatcher
import spock.lang.Specification

import static asteroid.Expressions.constX
import static asteroid.Expressions.mapEntryX
import static asteroid.Expressions.mapX

/**
 * Unit tests all {@link Expressions} methods
 *
 * @since 0.4.3
 */
class ExpressionsSpec extends Specification {

    void 'constX: create a primitive constant'() {
        given: 'a macro expression'
        ConstantExpression macroConstX = macro { 1 }

        and: 'an asteroid constant expression'
        ConstantExpression asteroidConstX = constX(1, true)

        expect: 'both to be the same'
        ASTMatcher.matches(macroConstX, asteroidConstX)

    }

    void 'mapEntryX: create map of type Map<String,?>'() {
        given: 'an ref expression'
        MapExpression ref = macro {
            [a: 1, b: 2]
        }

        and: 'asteroid counterpart'
        // We're using the mapX(List) because macro expects the map entries to be
        // inside a java.util.ArrayList instance instead of an java.util.Arrays$ArrayList
        // which is the type resulting of doing Arrays.asList (mapX(expression...))
        MapExpression asteroid = mapX(
                new ArrayList<MapEntryExpression>(
                        Arrays.asList(
                                mapEntryX('a', constX(1, true)),
                                mapEntryX('b', constX(2, true))
                        )
                )
        )

        expect: 'comparing entries'
        ASTMatcher.matches(ref, asteroid)
    }
}
