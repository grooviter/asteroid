package asteroid.statements.retry

import asteroid.A
import asteroid.AbstractLocalTransformation
import asteroid.Expressions
import asteroid.Phase
import asteroid.statements.retry.Log
import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.PropertyNode
import org.codehaus.groovy.ast.expr.BooleanExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.DoWhileStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.TryCatchStatement
import org.codehaus.groovy.syntax.Types

/**
 *  int counter = 0;
 *  do {
 *    try {
 *        buggyCall()
 *        counter = repeatTimes;
 *    } catch(Exception ex) {
 *        logger.error(ex);
 *        counter++;
 *    } finally {
 *        log.info "safe!!"
 *    }
 *  } while (conter<repeatTimes)
 */
@CompileStatic
@Phase(Phase.LOCAL.INSTRUCTION_SELECTION)
class RetryImpl extends AbstractLocalTransformation<Retry, MethodNode> {

    static final String EXCEPTION_VAR_NAME = 'ex'

    @Override
    void doVisit(final AnnotationNode annotation, final MethodNode methodNode) {
        // Logger logger
        PropertyNode loggerPropertyNode = A.NODES.property('logger').type(Log).build()
        methodNode.declaringClass.addProperty(loggerPropertyNode)
        VariableExpression loggerVarExpr = A.EXPR.varX('logger')

        // Integer counter
        PropertyNode counterPropertyNode = A.NODES.property('counter')
                .modifiers(A.ACC.ACC_PUBLIC)
                .type(Integer)
                .initialValueExpression(A.EXPR.constX('1'))
                .build()
        methodNode.declaringClass.addProperty(counterPropertyNode)
        VariableExpression counterVarExpr = A.EXPR.varX('counter')

        // retryTimes
        ConstantExpression retryNumberValueExpr = A.EXPR.constX(A.UTIL.ANNOTATION.get(annotation, Integer))

        // logger.error(ex)
        // counter++;
        Statement mutateNumberStmt = A.STMT.stmt(
                        A.EXPR.binX(
                                counterVarExpr,
                                    Types.PLUS_EQUAL,
                                A.EXPR.constX(1)))
        MethodCallExpression loggerErrorExpr = A.EXPR.callX(loggerVarExpr, 'error', A.EXPR.varX('ex'))
        Statement catchStmt = A.STMT.blockS(
                                    A.STMT.stmt(loggerErrorExpr),
                                    mutateNumberStmt)

        // logger.info('safe!!')
        MethodCallExpression loggerInfoExpr = A.EXPR.callX(loggerVarExpr, 'info', A.EXPR.constX('safe!!'))
        Statement finallyStmt = A.STMT.stmt(loggerInfoExpr)

        Statement finishCounter = A.STMT.stmt(
                                        A.EXPR.binX(
                                                counterVarExpr,
                                                    Types.EQUAL,
                                                retryNumberValueExpr))

        // try { buggy; counter = repeatTimes; } catch(Exception ex) { logger.error(ex); counter++; } finally { log.info "safe!!" }
        TryCatchStatement protectedCode = A.STMT.tryCatchSBuilder()
                .tryStmt(
                    A.STMT.blockS(
                            methodNode.code,
                            finishCounter))
                .addCatchStmt(Exception, EXCEPTION_VAR_NAME, catchStmt)
                .finallyStmt(finallyStmt)
                .build()

        // do { ... } while (conter<repeatTimes)
        DoWhileStatement doWhileStatement = A.STMT.doWhileStatement(
                A.EXPR.boolX(
                        counterVarExpr,
                        Types.COMPARE_LESS_THAN,
                        retryNumberValueExpr),
                protectedCode)

        // do { try { buggy; counter = repeatTimes;  } catch(Exception ex) { counter++; } finally { log.info "safe!!" } } while (conter<repeatTimes)
        methodNode.code = doWhileStatement
    }
}
