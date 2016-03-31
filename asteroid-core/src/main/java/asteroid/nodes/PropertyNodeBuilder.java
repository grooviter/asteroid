package asteroid.nodes;

import asteroid.A;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.Expression;

/**
 * Creates a new {@link PropertyNode} instance
 *
 * @since 0.1.4
 */
public class PropertyNodeBuilder {

    private String name;
    private int modifiers;
    private ClassNode type;
    private ClassNode owner;
    private Expression initialValueExpression;

    /* Private constructor */
    private PropertyNodeBuilder(String name) {
        this.name = name;
    }

    /**
     * Creates a builder from the property name
     *
     * @param name the name of the property
     * @return an instance of {@link PropertyNodeBuilder}
     * @since 0.1.4
     */
    public static PropertyNodeBuilder property(String propertyName) {
        return new PropertyNodeBuilder(propertyName);
    }

    /**
     * Sets property modifiers: static/public/...
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>property('solution')
     * .modifiers(A.ACC.ACC_PUBLIC)
     * .build()</code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>public Object solution</code></pre>
     *
     * @param modifiers use {@link A#ACC} to set property modifiers
     * @return current instance of {@link PropertyNodeBuilder}
     * @since 0.1.4
     */
    public PropertyNodeBuilder modifiers(int modifiers) {
        this.modifiers = modifiers;
        return this;
    }

    /**
     * Sets the type of the property
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>property('solution')
     * .modifiers(A.ACC.ACC_PUBLIC)
     * .type(String)
     * .build()</code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>public String solution</code></pre>
     *
     * @param type the {@link Class} of the property
     * @return current instance of {@link PropertyNodeBuilder}
     * @since 0.1.4
     */
    public PropertyNodeBuilder type(Class type) {
        this.type = A.NODES.clazz(type).build();
        return this;
    }

    /**
     * Sets the property owner
     *
     * @param owner to which class node this property belongs
     * @return current instance of {@link PropertyNodeBuilder}
     * @since 0.1.4
     */
    public PropertyNodeBuilder owner(ClassNode owner) {
        this.owner = owner;
        return this;
    }

    /**
     * Sets an initial value for the property.
     * <strong>AST</strong>
     * <pre><code>property('solution')
     * .modifiers(A.ACC.ACC_PUBLIC)
     * .type(String)
     * .initialValue(A.EXPR.constX('42'))
     * .build()</code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>public String solution = "42"</code></pre>
     *
     * @param initialValueExpression the initial value of the property
     * @return current instance of {@link PropertyNodeBuilder}
     * @since 0.1.4
     */
    public PropertyNodeBuilder initialValueExpression(Expression initialValueExpression) {
        this.initialValueExpression = initialValueExpression;
        return this;
    }

    /**
     * Returns the initialized {@link PropertyNode}
     *
     * @return the {@link PropertyNode} instance built by this builder
     * @since 0.1.4
     */
    public PropertyNode build() {
        if (type == null) {
            type = A.NODES.clazz(Object.class).build();
        }

        return new PropertyNode(name, modifiers, type, owner, initialValueExpression, null, null);
    }
}
