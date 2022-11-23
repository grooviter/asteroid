package asteroid.expressions


import asteroid.A
import asteroid.Phase
import org.codehaus.groovy.syntax.Types
import asteroid.AbstractLocalTransformation

import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.BooleanExpression
import groovy.transform.CompileStatic

/**
 * @since 0.2.4
 */
@CompileStatic
@Phase(Phase.LOCAL.INSTRUCTION_SELECTION)
class AddOneToParamImpl extends AbstractLocalTransformation<AddOneToParam, MethodNode> {

    @Override
    void doVisit(final AnnotationNode annotation, final MethodNode methodNode) {
        Parameter[] params = methodNode.parameters
        Parameter param = params.first()

        Expression magicNumberX = A.EXPR.varDeclarationX('magicNumber', Integer, A.EXPR.constX(42))
        BooleanExpression isNumber = A.EXPR.boolIsInstanceOfX(A.EXPR.varX(param.name), Number)
        BooleanExpression isMagicNumber = A.EXPR.boolX(param.name, Types.COMPARE_NOT_EQUAL, 'magicNumber')
        Statement mutateNumberStmt = A.STMT.stmt(A.EXPR.binX(A.EXPR.varX(param.name),
                                                             Types.EQUAL,
                                                             A.EXPR.binX(A.EXPR.varX(param.name),
                                                                         Types.PLUS,
                                                                         A.EXPR.constX(1))))

        Statement throwExceptionStmt =
            A.STMT.throwS(A.EXPR.newX(IllegalStateException,
                                      A.EXPR.constX("check if param is not a number or maybe the magic number!")))

        IfStatement ifStatement =
            A.STMT.ifElseS(A.EXPR.landX(isNumber, isMagicNumber),
                           mutateNumberStmt,
                           throwExceptionStmt)

            methodNode.code = A.STMT.blockS(A.STMT.stmt(magicNumberX),
                                            ifStatement,
                                            methodNode.code)
    }
}
