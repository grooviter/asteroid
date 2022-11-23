package asteroid.statements.trycatch

import asteroid.A
import asteroid.Phase
import asteroid.AbstractLocalTransformation
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.PropertyNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.TryCatchStatement
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression

import groovy.transform.CompileStatic

/**
 *  try {
 *      unsafeCall()
 *  } catch(Exception ex) {
 *      return defaultValue
 *  } finally {
 *      log.info "safe!!"
 *  }
 *
 */
@CompileStatic
@Phase(Phase.LOCAL.INSTRUCTION_SELECTION)
class SafeImpl extends AbstractLocalTransformation<Safe, MethodNode> {

    static final String EXCEPTION_VAR_NAME = 'ex'

    @Override
    void doVisit(final AnnotationNode annotation, final MethodNode methodNode) {
        // Logger logger
        PropertyNode loggerPropertyNode = A.NODES.property('logger')
            .type(Log)
            .build()

        methodNode.declaringClass.addProperty(loggerPropertyNode)

        // logger.error(ex)
        VariableExpression loggerVarExpr = A.EXPR.varX('logger')
        ConstantExpression defaultValueExpr = A.EXPR.constX(A.UTIL.NODE.get(annotation, Integer))
        MethodCallExpression loggerErrorExpr = A.EXPR.callX(loggerVarExpr, 'error', A.EXPR.varX('ex'))
        Statement catchStmt = A.STMT.blockS(
            A.STMT.stmt(loggerErrorExpr),
            A.STMT.returnS(defaultValueExpr))

        // logger.info('safe!!')
        MethodCallExpression loggerInfoExpr = A.EXPR.callX(loggerVarExpr, 'info', A.EXPR.constX('safe!!'))
        Statement finallyStmt = A.STMT.stmt(loggerInfoExpr)

        TryCatchStatement protectedCode = A.STMT.tryCatchSBuilder()
            .tryStmt(methodNode.code)
            .addCatchStmt(Exception, EXCEPTION_VAR_NAME, catchStmt)
            .finallyStmt(finallyStmt)
            .build()

        methodNode.code = protectedCode
    }
}
