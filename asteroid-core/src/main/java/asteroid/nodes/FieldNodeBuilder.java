package asteroid.nodes;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.expr.Expression;

/**
 * Helps to build and instance of type {@link FieldNode}
 *
 * @since 0.4.3
 */
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
final public class FieldNodeBuilder {

    private final  String name;

    private int modifiers;
    private ClassNode type;
    private ClassNode owner;
    private Expression initialExpression;

    private FieldNodeBuilder(final String name) {
        this.name = name;
    }

    /**
     * Creates a builder with the field's name
     *
     * @param name the name of the field to create
     * @return an instance of {@link FieldNodeBuilder}
     * @since 0.4.3
     */
    public static FieldNodeBuilder field(final String name) {
        return new FieldNodeBuilder(name);
    }

    /**
     * The access modifiers for the field to create
     *
     * @param modifiers field modifiers
     * @return the current builder instance
     * @since 0.4.3
     */
    public FieldNodeBuilder modifiers(final int modifiers) {
        this.modifiers = modifiers;
        return this;
    }

    /**
     * Sets the type of the {@link FieldNode}
     *
     * @param type the type of the field to create
     * @return the current builder instance
     * @since 0.4.3
     */
    public FieldNodeBuilder type(final ClassNode type) {
        this.type = type;
        return this;
    }

    /**
     * Sets the type of the {@link FieldNode}
     *
     * @param type the type of field to create
     * @return the current builder instance
     * @since 0.4.3
     */
    public FieldNodeBuilder type(final Class<?> type) {
        this.type = ClassNodeBuilder.clazz(type).build();
        return this;
    }

    /**
     * Sets the type of the instance containing the field
     *
     * @param owner the node containing the field
     * @return the current builder instance
     * @since 0.4.3
     */
    public FieldNodeBuilder owner(final ClassNode owner) {
        this.owner = owner;
        return this;
    }

    /**
     * Sets the initial expression of the {@link FieldNode}
     *
     * @param initialExpresion expression to initialize the field
     * @return the current builder instance
     * @since 0.4.3
     */
    public FieldNodeBuilder expression(final Expression initialExpression) {
        this.initialExpression = initialExpression;
        return this;
    }

    /**
     * Once the instance configuration has been finished, this method
     * will return the configured instance.
     *
     * @return the configured {@link FieldNode}
     * @since 0.4.3
     */
    public FieldNode build() {
        return new FieldNode(this.name,
                             this.modifiers,
                             this.type,
                             this.owner,
                             this.initialExpression);
    }
}
