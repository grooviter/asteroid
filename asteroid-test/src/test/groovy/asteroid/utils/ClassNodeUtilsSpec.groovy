package asteroid.utils

import asteroid.A
import asteroid.spec.AsteroidSpec
import org.codehaus.groovy.ast.ClassNode

class ClassNodeUtilsSpec extends AsteroidSpec {

    class DummyClass {
        String name
        List<Integer> years
    }

    void 'check fields are of certain type'() {
        given: 'a type'
            ClassNode classNode = A.NODES.clazz(DummyClass).build()

        expect: 'it responds true'
            A.UTIL.NODE.hasFieldOfType(classNode, List.name)
            A.UTIL.NODE.hasFieldOfType(classNode, String.name)
    }
}
