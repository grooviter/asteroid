package asteroid.utils;

import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.stmt.BlockStatement;

public class NodeUtils {

    public BlockStatement getCodeBlock(final MethodNode methodNode) {
        return (BlockStatement) methodNode.getCode();
    }

}
