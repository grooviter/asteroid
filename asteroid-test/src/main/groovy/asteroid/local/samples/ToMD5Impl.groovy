package asteroid.local.samples

import asteroid.A
import asteroid.LocalTransformation
import asteroid.AbstractLocalTransformation

import groovy.transform.CompileStatic

import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.stmt.BlockStatement

@CompileStatic
@LocalTransformation(A.PHASE_LOCAL.SEMANTIC_ANALYSIS) // <1>
class ToMD5Impl extends AbstractLocalTransformation<ToMD5, FieldNode> { // <2>

    @Override
    void doVisit(AnnotationNode annotation, FieldNode node) { // <3>
        BlockStatement block  = buildMethodCode(node.name)
        MethodNode methodNode = A.NODES.method("${node.name}ToMD5")
            .modifiers(A.ACC.ACC_PUBLIC)
            .returnType(String)
            .code(block)
            .build()

        A.UTIL.CLASS.addMethod(node.declaringClass, methodNode)
    }

    private BlockStatement buildMethodCode(final String name) {
        A.STMT.blockSFromString """
            return java.security.MessageDigest
                .getInstance('MD5')
                .digest(${name}.getBytes())
                .encodeHex()
                .toString()
        """
    }

}
