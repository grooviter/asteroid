package asteroid.nodes;

import asteroid.A;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.expr.Expression;

/**
 * Builder to create instances of type {@link AnnotationNode}
 *
 * @since 0.1.0
 */
public class AnnotationNodeBuilder {

    private final AnnotationNode annotationNode;

    private AnnotationNodeBuilder(final AnnotationNode annotationNode) {
        this.annotationNode = annotationNode;
    }

    /**
     * Creates an instance of {@link AnnotationNodeBuilder}
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>annotation(MyAnnotation.class).build()</code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>@MyAnnotation</code></pre>
     *
     * @param clazz the annotation type
     * @return an instance of {@link AnnotationNodeBuilder}
     */
    public static AnnotationNodeBuilder annotation(Class clazz) {
        return new AnnotationNodeBuilder(new AnnotationNode(A.NODES.clazz(clazz).build()));
    }

    /**
     * Adds a new member to the annotation
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>annotation(MyAnnotation.class)
     * .member('value', classX(String.class))
     * .build()</code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>@MyAnnotation(String.class)</code></pre>
     *
     * @param name the name of the new member
     * @param expression the  value of the new member. It could be {@link org.codehaus.groovy.ast.expr.PropertyExpression}
     * {@link org.codehaus.groovy.ast.expr.ClassExpression}, or a {@link org.codehaus.groovy.ast.expr.ConstantExpression}
     * @return the current instance of {@link AnnotationNodeBuilder}
     */
    public AnnotationNodeBuilder member(String name, Expression expression) {
        this.annotationNode.addMember(name, expression);

        return this;
    }

    /**
     * Returns the annotation node configured by this builder
     *
     * @return an instance of {@link AnnotationNode}
     */
    public AnnotationNode build() {
        return this.annotationNode;
    }

}
