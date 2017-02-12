package asteroid.utils

import asteroid.A
import asteroid.spec.AsteroidSpec
import org.codehaus.groovy.ast.ClassNode

class MiscellaneousUtilsSpec extends AsteroidSpec {

    void 'combine closures with and'() {
        setup:
        def fn = A.UTIL.MISC.and({ x -> x % 2 == 0 },
                                 { y -> y > 0 })
        expect:
        fn(x) == y

        where:
        x | y
        0 | false
        1 | false
        2 | true
    }

    void 'combine closures with or'() {
        setup:
        def fn = A.UTIL.MISC.or({ x -> x % 2 == 0 },
                                { y -> y > 0 })
        expect:
        fn(x) == y

        where:
        x  | y
        -1 | false
        0  | true
        1  | true
        2  | true
    }
}
