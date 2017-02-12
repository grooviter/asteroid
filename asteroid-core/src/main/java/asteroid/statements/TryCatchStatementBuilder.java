package asteroid.statements;

import asteroid.A;

import java.util.List;
import java.util.ArrayList;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.TryCatchStatement;

/**
 * This builder tries to make it easier to build a try/catch
 * statement.<br/><br/>
 *
 * <strong>AST</strong>
 * <pre><code>TryCatchStatement stmt = A.STMT.tryCatchSBuilder()
 *        .tryStmt(protectedStmt)
 *        .addCatchStmt(Exception, 'ex', handlerOneStmt)
 *        .addCatchStmt(IllegalStatementException, 'ix', handlerTwoStmt)
 *        // ... add as many catch as you need
 *        .finallyStmt(finallyStatement)
 *        .build()</code></pre>
 *
 * <strong>Result</strong>
 * <pre><code>try {
 *    // protectedStmt
 *   } catch(Exception ex) {
 *    // handlerOneStmt
 *   } catch(IllegalStateException ix) {
 *    // handlerTwoStmt
 *   } finally {
 *    // finallyStatement
 *   }</code></pre>
 *
 *
 * @since 0.2.3
 */
public class TryCatchStatementBuilder {

    private final List<CatchStatement> catchStatements = new ArrayList<>();

    private Statement tryStatement;
    private Statement finallyStatement;

    /**
     * Adds the try statement
     *
     * @param stmt the code you want to protect
     * @return the current builder instance
     * @since 0.2.3
     */
    public TryCatchStatementBuilder tryStmt(final Statement stmt) {
        tryStatement = stmt;
        return this;
    }

    /**
     * Adds one catch statement to catch a specific exception giving a
     * specific name for the exception variable.
     *
     * </br></br><b>IMPORTANT: </b>
     * This method uses {@link Class} to specify the class of the {@link Exception}. Don't
     * use it if you are targetting a very early compilation stage. If so use the method
     * using {@link ClassNode}
     *
     * @param exceptionType the {@link Class} of the exception you want to catch
     * @param exceptionVarName the name of the exception variable
     * @param stmt the code handling the exception
     * @return the current builder instance
     * @since 0.2.3
     */
    public TryCatchStatementBuilder addCatchStmt(final Class exceptionType, final String exceptionVarName, final Statement stmt) {
        return this.addCatchStmt(A.NODES.clazz(exceptionType).build(), exceptionVarName, stmt);
    }

    /**
     * Adds one catch statement to catch a specific exception giving a
     * specific name for the exception variable.
     *
     * @param exceptionType the {@link ClassNode} of the exception you want to catch
     * @param exceptionVarName the name of the exception variable
     * @param stmt the code handling the exception
     * @return the current builder instance
     * @since 0.2.3
     */
    public TryCatchStatementBuilder addCatchStmt(final ClassNode exceptionType, final String exceptionVarName, final Statement stmt) {
        final Parameter param = A.NODES.param(exceptionVarName).type(exceptionType).build();
        final CatchStatement catchStatement = new CatchStatement(param, stmt);

        catchStatements.add(catchStatement);
        return this;
    }

    /**
     * Adds the statement that will be placed inside the finally block
     *
     * @param stmt the code executed in the finally block
     * @return the current builder instance
     * @since 0.2.3
     */
    public TryCatchStatementBuilder finallyStmt(final Statement stmt) {
        finallyStatement = stmt;
        return this;
    }

    /**
     * Returns the created {@link TryCatchStatement} instance. At
     * least the "try" statement and the "finally" statement must be
     * provided. Otherwise it will throw a {@link
     * IllegalStateException}
     *
     * @return a {@link TryCatchStatement} instance
     * @throws IllegalStateException if neither try nor finally statements are provided
     * @since 0.2.3
     */
    public TryCatchStatement build() {
        if (tryStatement == null || finallyStatement == null) {
            throw new IllegalStateException("Both try statement and finally statement must be provided");
        }

        final TryCatchStatement tryCatchStmt = new TryCatchStatement(tryStatement, finallyStatement);

        if (!catchStatements.isEmpty()) {
            tryCatchStmt
                .getCatchStatements()
                .addAll(catchStatements);
        }

        return tryCatchStmt;
    }
}
