package asteroid.utils;

import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;

/**
 * Utility classes to deal with {@link AnnotationNode} instances
 *
 * @since 0.1.4
 * @see asteroid.Utils
 */
public final class AnnotationNodeUtils {

    public static final String ANNOTATION_VALUE = "value";

    /**
     * Returns the {@link String} representation of the member "value" of the annotation
     * passed as parameter
     *
     * @param annotationNode The node we want the value from
     * @return the value of the member "value" as a {@link String}
     * @since 0.1.4
     */
    public String getStringValue(final AnnotationNode annotationNode) {
        Expression expr = annotationNode.getMember(ANNOTATION_VALUE);
        String annValue = expr != null ? annotationNode.getMember(ANNOTATION_VALUE).getText() : null;

        return annValue;
    }

    /**
     * Returns the value of a given annotation member. The value is casted to the type passed as second
     * argument.
     *
     * @param annotationNode The node we want the member value from
     * @param clazz the type of value expected
     * @param <T> The expected return type
     * @return an instance of type T
     * @since 0.1.4
     */
    public <T> T get(AnnotationNode annotationNode, final Class<T> clazz) {
        Object value = resolveValueFrom(annotationNode.getMember(ANNOTATION_VALUE));

        return clazz.cast(value);
    }

    /**
     * Returns the value of a member of the {@link AnnotationNode} passed as first parameter with the name
     * passed as second parameter. The third parameter is the expected type for the member. There are
     * a set of rules before trying to convert values to the {@link Class} passed as third parameter:
     * <ul>
     *     <li>If instance of {@link ClassExpression} a {@link String} with the qualified name will be returned</li>
     *     <li>If instance of {@link ConstantExpression} the raw value will be returned</li>
     *     <li>If isntance of {@link PropertyExpression} the value will be converted to String</li>
     * </ul>
     * Then there will be a cast to the specific {@link Class}
     *
     * @param annotationNode the annotation we want a member value from
     * @param name the name of the member we want the value from
     * @param clazz the clazz of the expected value
     * @since 0.1.4
     */
    public <T> T get(AnnotationNode annotationNode, String name, Class<T> clazz) {
        Object value = resolveValueFrom(annotationNode.getMember(name));

        return clazz.cast(value);
    }

   private Object resolveValueFrom(final Expression expression) {
        if (expression instanceof ClassExpression) {
            return ClassExpression.class.cast(expression).getText();

        } else if (expression instanceof ConstantExpression) {
            return ConstantExpression.class.cast(expression).getValue();

        } else if (expression instanceof PropertyExpression) {
            return PropertyExpression.class.cast(expression).getPropertyAsString();
        }

        return expression.toString();
    }
}
