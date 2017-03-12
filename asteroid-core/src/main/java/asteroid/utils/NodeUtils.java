package asteroid.utils;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.stmt.BlockStatement;

/**
 * General utility methods to deal with {@link ASTNode} instances
 *
 * @since 0.1.4
 * @see asteroid.Utils
 */
public class NodeUtils {

    /**
     * Utility method to make your transformation code more compile
     * static compliant when getting a method node code block. Many
     * times you may want to deal with it as if it were a {@link
     * BlockStatement}.
     *
     * @param methodNode the method we want the code from
     * @return the method code as a {@link BlockStatement}
     */
    public BlockStatement getCodeBlock(final MethodNode methodNode) {
        return (BlockStatement) methodNode.getCode();
    }
}
