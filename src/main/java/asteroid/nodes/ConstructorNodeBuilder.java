package asteroid.nodes;

import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.stmt.Statement;

/**
 * Builder to create instance of type {@link ConstructorNode}
 *
 * @since 0.1.0
 */
public class ConstructorNodeBuilder {

    private int modifiers;
    private Statement code;

    private ConstructorNodeBuilder(int modifiers) {
        this.modifiers = modifiers;
    }

    /**
     * @param modifiers
     * @return an instance of type {@link ConstructorNodeBuilder}
     */
    public static ConstructorNodeBuilder constructor(int modifiers) {
        return new ConstructorNodeBuilder(modifiers);
    }

    /**
     * @param code
     * @return an instance of type {@link ConstructorNodeBuilder}
     */
    public ConstructorNodeBuilder code(Statement code) {
        this.code = code;
        return this;
    }

    /**
     *
     * @return an instance of type {@link ConstructorNode}
     */
    public ConstructorNode build() {
        return new ConstructorNode(modifiers, code);
    }

}
