package asteroid.nodes;

import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.stmt.Statement;

/**
 * Builder to create instance of type {@link ConstructorNode}
 *
 * e.g. having a class named "MyClass" if we liked to create
 * a default constructor, we can achieve it by writing
 * the following:
 * <br><br>
 * <strong>AST</strong>
 * <pre><code>
 * constructor(A.ACC.ACC_PUBLIC)
 * .code(callThisX("println", constX("constructor initialized")))
 * .build()</code></pre>
 * <strong>Result</strong>
 * <pre><code>
 * public MyClass() {
 *     println "constructor initialized"
 * }
 * </code></pre>
 *
 * @since 0.1.5
 */
public class ConstructorNodeBuilder {

    private int modifiers;
    private Statement code;

    private ConstructorNodeBuilder(int modifiers) {
        this.modifiers = modifiers;
    }

    /**
     * Creates a {@link ConstructorNodeBuilder} instance initializing
     * constructor with modifiers
     *
     * @param modifiers method modifiers
     * @return an instance of type {@link ConstructorNodeBuilder}
     * @since 0.1.5
     */
    public static ConstructorNodeBuilder constructor(int modifiers) {
        return new ConstructorNodeBuilder(modifiers);
    }

    /**
     * Sets the constructor body code
     *
     * @param code the body part of the constructor
     * @return an instance of type {@link ConstructorNodeBuilder}
     * @since 0.1.5
     */
    public ConstructorNodeBuilder code(Statement code) {
        this.code = code;
        return this;
    }

    /**
     * Returns the configured instance of type {@link ConstructorNode}
     *
     * @return an instance of type {@link ConstructorNode}
     * @since 0.1.5
     */
    public ConstructorNode build() {
        return new ConstructorNode(modifiers, code);
    }
}
