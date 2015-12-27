package examples

import asteroid.A
import asteroid.LocalTransformation
import asteroid.LocalTransformationImpl

import groovy.transform.CompileStatic

import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.ClassExpression
import org.codehaus.groovy.ast.expr.FieldExpression
import org.codehaus.groovy.control.SourceUnit

import java.security.MessageDigest

@CompileStatic
@LocalTransformation(A.PHASE_LOCAL.SEMANTIC_ANALYSIS) // <1>
class ToMD5Impl extends LocalTransformationImpl<ToMD5, FieldNode> { // <2>

    @Override
    void doVisit(
        final AnnotationNode annotation,
        final FieldNode fieldNode,// <3>
        final SourceUnit source) {
        MethodNode methodNode = A.NODES.method("${fieldNode.name}ToMD5")
            .returnType(String)
            .modifiers(A.ACC.ACC_PUBLIC)
            .code(getMethodCode(fieldNode))
            .build()

        A.UTIL.addMethodToClass(fieldNode.declaringClass, methodNode)
    }

    private ReturnStatement getMethodCode(final FieldNode fieldNode) {
        FieldExpression field    = A.EXPR.fieldX(fieldNode)
        ClassExpression mDigest  = A.EXPR.classX(MessageDigest)

        def getBytes = A.EXPR.callX(field,   "getBytes",    A.EXPR.constX("UTF-8"))
        def md5Inst  = A.EXPR.callX(mDigest, "getInstance", A.EXPR.constX("MD5"))
        def digest   = A.EXPR.callX(md5Inst, "digest",      getBytes)
        def encode   = A.EXPR.callX(digest,  "encodeHex")
        def toString = A.EXPR.callX(encode,  "toString")

        return A.STMT.returnS(toString)
    }

}
