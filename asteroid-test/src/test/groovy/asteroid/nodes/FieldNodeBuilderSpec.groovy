package asteroid.nodes

import static org.codehaus.groovy.transform.AbstractASTTransformUtil.ACC_PUBLIC
import asteroid.A
import spock.lang.Specification
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.ClassNode

/**
 * Checks building a new {@link FieldNode}
 * @since 0.4.3
 */
class FieldNodeBuilderSpec extends Specification {

    void 'check simple field node'() {
        given: 'an instance of the builder'
        FieldNodeBuilder builder = FieldNodeBuilder.field('count')

        and: 'a given owner class node'
        ClassNode owner = A.NODES.clazz(String).build()

        when: 'setting the type'
        FieldNode nameFieldNode = builder
            .type(Integer)
            .modifiers(ACC_PUBLIC)
            .owner(owner)
            .expression(A.EXPR.constX(1))
            .build()

        then: 'checking field node properties'
        nameFieldNode.name                    == 'count'
        nameFieldNode.type.name               == 'java.lang.Integer'
        nameFieldNode.modifiers               == ACC_PUBLIC
        nameFieldNode.initialExpression.value == 1

        and: 'no owner this time'
        nameFieldNode.owner.typeClass         == String
    }
}
