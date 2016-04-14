package asteroid.utils;

import groovy.lang.Closure;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

import asteroid.A;
import asteroid.utils.StatementUtils.Group;

/**
 * Utility functions to add checks to {@link
 * MethodNode}. <b>Checks</b> are a very basic help to code in a
 * contract way in your code very much like other frameworks do like
 * <a href="https://github.com/spockframework">Spock</a>
 * <br><br>
 * Imagine you had a class with a method like:
 * <pre></code>
 * void myOperation(Integer number) {
 *    check: 'number is greater than 5'
 *    number > 5
 *
 *    then: 'print that number plus one'
 *    println number + 1
 * }
 * </code></pre>
 *
 * If you have targeted that method using {@link CheckUtils#addCheckTo} the
 * expected behavior when calling <b>myOperation(4)</b> is the code to fail
 * using the text in the statement label.
 * <br><br>
 * <b class="note">Checks are used in Asteroid local transformations by default</b>
 *
 * @since 0.1.5
 * @see asteroid.Utils
 */
public class CheckUtils {

    /**
     * Adds checks to the method node passed as parameter
     *
     * @param methodNode the node we want to add the checks to
     * @since 0.1.5
     */
    public void addCheckTo(final MethodNode methodNode) {
        BlockStatement blockStmt   = A.UTIL.NODE.getCodeBlock(methodNode);
        List<Group> groups         = A.UTIL.STMT.groupStatementsByLabel(blockStmt);
        List<Statement> statements = A.UTIL.STMT.applyToStatementsByLabelFlatten(groups, getMappings());

        // #TODO it will remove to enforce the use of checks
        if (!groups.isEmpty()) {
            methodNode.setCode(A.STMT.blockS(statements));
        }
    }

    private Map<String, Closure<Statement>> getMappings() {
        Map<String, Closure<Statement>> mappings = new HashMap<String, Closure<Statement>>();
        mappings.put("check", buildAssertionStmt());

        return mappings;
    }

    private Closure<Statement> buildAssertionStmt() {
        return new Closure<Statement>(null) {
            public Statement doCall(final Group group, final ExpressionStatement stmt) {
                return createAssertStatement(group, stmt);
            }
        };
    }

    private Statement createAssertStatement(final Group group, final ExpressionStatement stmt) {
        return A.STMT.assertS(A.EXPR.boolX(stmt.getExpression()), group.label.desc);
    }

}
