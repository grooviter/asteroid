package asteroid.statements.retry

import asteroid.A
import asteroid.AbstractLocalTransformation
import asteroid.Phase
import java.util.logging.Logger
import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.PropertyNode
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.BooleanExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.WhileStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.TryCatchStatement
import org.codehaus.groovy.syntax.Types

/**
 *  AtomicInteger counter = new AtomicInteger(0);
 *
 *  do {
 *    try {
 *        buggyCall()
 *    } catch(Exception ex) {
 *        logger.severe(ex.getMessage());
 *    } finally {
 *        log.info "safe!!"
 *    }
 *  } while (counter.getAndAdd(1) <= repeatTimes)
 */
@CompileStatic
@Phase(Phase.LOCAL.INSTRUCTION_SELECTION)
class RetryImpl extends AbstractLocalTransformation<Retry, MethodNode> {

    @Override
    void doVisit(final AnnotationNode annotation, final MethodNode methodNode) {
        // Integer counter
        ClassNode declaringClass = methodNode.declaringClass

        A.UTIL.NODE.addPropertyIfNotPresent(declaringClass, counterPropertyNode)
        A.UTIL.NODE.addPropertyIfNotPresent(declaringClass, getLoggerPropertyNode(declaringClass))

        VariableExpression counterVarExpr = A.EXPR.varX('counter')

        // retryTimes
        ConstantExpression retryNumberValueExpr = A
            .EXPR
            .constX(A.UTIL.NODE.get(annotation, Integer))

        // log.severe(ex)
        MethodCallExpression loggerErrorExpr = logExpr('severe', A.EXPR.callX(A.EXPR.varX('ex'), 'getMessage'))
        Statement catchStmt = A
            .STMT
            .blockS(A.STMT.stmt(loggerErrorExpr))

        // log.info('safe!!')
        MethodCallExpression loggerInfoExpr = logExpr('info', A.EXPR.constX('safe!!'))
        Statement finallyStmt = A.STMT.stmt(loggerInfoExpr)

        // try/catch/finally
        TryCatchStatement protectedCode = A
            .STMT
            .tryCatchSBuilder()
            .tryStmt(A.STMT.blockS(methodNode.code))
            .addCatchStmt(Exception, 'ex', catchStmt)
            .finallyStmt(finallyStmt)
            .build()

        // do { ... } while (counter<repeatTimes)
        BooleanExpression counterLessEqualsRepeatTimes = A
            .EXPR
            .boolX(A.EXPR.callX(A.EXPR.varX('counter'), 'getAndAdd', A.EXPR.constX(1)),
                   Types.COMPARE_LESS_THAN_EQUAL,
                   retryNumberValueExpr)

        WhileStatement whileS = A
            .STMT
            .whileS(counterLessEqualsRepeatTimes, protectedCode)

        // method code
        methodNode.code = whileS
    }

    PropertyNode getCounterPropertyNode() {
        PropertyNode counterPropertyNode = A.NODES.property('counter')
            .modifiers(A.ACC.ACC_PUBLIC)
            .type(java.util.concurrent.atomic.AtomicInteger)
            .initialValueExpression(initializateAtomicInteger())
            .build()

        return counterPropertyNode
    }

    PropertyNode getLoggerPropertyNode(ClassNode classNode) {
        // = Logger.getLogger(MyClass.getClass().getName())
        Expression newLogger = A
            .EXPR
            .staticCallX(Logger,
                         'getLogger',
                         A.EXPR.callX(A.EXPR.staticCallX(classNode, 'getClass'), 'getName'))

        return A.NODES.property('logger')
            .modifiers(A.ACC.ACC_PUBLIC)
            .type(Logger)
            .initialValueExpression(newLogger)
            .build()
    }

    MethodCallExpression logExpr(String level, Expression expression) {
        return A.EXPR.callX(A.EXPR.varX('logger'), level, expression)
    }

    AnnotationNode getLoggerAnnotation() {
        return A.NODES.annotation(groovy.util.logging.Log).build()
    }

    Expression initializateAtomicInteger() {
        return A.EXPR.newX(java.util.concurrent.atomic.AtomicInteger, A.EXPR.constX(0))
    }
}
