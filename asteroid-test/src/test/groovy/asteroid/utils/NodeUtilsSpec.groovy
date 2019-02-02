package asteroid.utils

import spock.lang.Specification
import asteroid.nodes.FieldNodeBuilder
import asteroid.nodes.ClassNodeBuilder
import asteroid.nodes.MethodNodeBuilder
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode

/**
 * Checks methods in {@link NodeUtils}
 *
 * @since 0.4.3
 */
class NodeUtilsSpec extends Specification {

    void 'addGeneratedMethod: Add a method annotated with @Generated'() {
        given: 'a class node'
        ClassNode classNode = ClassNodeBuilder.clazz(String).build()

        and: 'a new method'
        MethodNode hashString = MethodNodeBuilder
            .method('hashIt')
            .returnType(String)
            .code(macro { return "hash" })
            .build()

        and: 'and instance of NodeUtils'
        NodeUtils utils = new NodeUtils()

        when: 'adding a method to that class'
        utils.addGeneratedMethod(classNode, hashString)

        and: 'looking for the added method in the class node'
        MethodNode node = utils.findMethodByName(classNode, 'hashIt')

        then: 'we should find the annotation in the method'
        node.annotations.first().classNode.name == 'groovy.transform.Generated'
    }

    void 'addGeneratedField: Add a field annotated with @Generated'() {
        given: 'a class node'
        ClassNode classNode = ClassNodeBuilder.clazz(String).build()

        and: 'a new field'
        FieldNode fieldNode = FieldNodeBuilder
            .field('something')
            .type(String)
            .build()

        and: 'an instance of NodeUtils'
        NodeUtils utils = new NodeUtils()

        when: 'adding the field to that class'
        utils.addGeneratedField(classNode, fieldNode)

        and: 'looking for the added field'
        FieldNode field = utils.findFieldByName(classNode, 'something')

        then: 'we should find the annotation'
        field.annotations.first().classNode.name == 'groovy.transform.Generated'
    }
}
