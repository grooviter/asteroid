package asteroid;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.first;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.last;

import asteroid.check.Result;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.tools.GeneralUtils;

import java.util.List;

/**
 * This class gathers together a bunch of util functions to deal with
 * AST transformations.
 *
 * @since 0.1.0
 */
public final class Utils {

    public static final String ANNOTATION_VALUE = "value";

    /**
     * Adds the method to the class node passed as first argument
     *
     * @param classNode the class we want to add the method to
     * @param methodNode the method we want to add
     * @since 0.1.0
     */
    public void addMethod(final ClassNode classNode, final MethodNode methodNode) {
        classNode.addMethod(methodNode);
    }

    /**
     * Makes the {@link ClassNode} to implement the interfaces passed
     * as arguments
     *
     * @param classNode
     * @param interfaces the interfaces we want the class node to be
     * implementing
     * @since 0.1.0
     */
    public void addInterfaces(final ClassNode classNode, Class... interfaces) {
        for (Class clazz : interfaces) {
            ClassNode nextInterface = ClassHelper.make(clazz, false);
            classNode.addInterface(nextInterface);
        }
    }

    /**
     * Returns the {@link String} representation of the member "value" of the annotation
     * passed as parameter
     *
     * @param annotationNode The node we want the value from
     * @return the value of the member "value" as a {@link String}
     * @since 0.1.0
     */
    public String getStringValue(final AnnotationNode annotationNode) {
        return annotationNode.getMember(ANNOTATION_VALUE).getText();
    }

    /**
     * Returns the first node of the list passed as parameter with the expected type T
     *
     * @param nodes a list of nodes
     * @param clazz the type of the expected value
     * @param <T> the expected type
     * @return a node with type T
     * @since 0.1.0
     */
    public <T> T getFirstNodeAs(final ASTNode[] nodes, final Class<T> clazz) {
        return (T) first(nodes);
    }

    /**
     * Returns the last node of the list passed as parameter with the expected type T
     *
     * @param nodes a list of nodes
     * @param clazz the type of the expected value
     * @param <T> the expected type
     * @return a node with type T
     * @since 0.1.0
     */
    public <T> T getLastNodeAs(final ASTNode[] nodes, final Class<T> clazz) {
        return (T) last(nodes);
    }

    /**
     * Returns the value of a given annotation member. The value is casted to the type passed as second
     * argument.
     *
     * @param annotationNode The node we want the member value from
     * @param clazz the type of value expected
     * @param <T> The expected return type
     * @return an instance of type T
     * @since 0.1.0
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
     * @since 0.1.0
     */
    public <T> T get(AnnotationNode annotationNode, String name, Class<T> clazz) {
        Object value = resolveValueFrom(annotationNode.getMember(name));

        return clazz.cast(value);
    }

    /**
     * Returns all properties from a given {@link ClassNode} passed as
     * argument
     *
     * @param classNode the {@link ClassNode} we want its properties from
     * @return a list of the properties ({@link FieldNode}) of a given
     * {@link ClassNode}
     * @since 0.1.0
     */
    public List<FieldNode> getInstancePropertyFields(ClassNode classNode) {
        return GeneralUtils.getInstancePropertyFields(classNode);
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

    /**
     * When executing a checker the result will be wrapped in a {@link Result} instance. This
     * method ease the way of creating a {@link Result} instance.
     *
     * @param passes whether the result is successful or not
     * @param node the node under test
     * @param errorMessage The message in case result was not successful
     * @since 0.1.0
     */
    public Result createResult(Boolean passes, ASTNode node, String errorMessage) {
        return new Result(node, passes ? Result.Status.PASSED : Result.Status.ERROR, errorMessage);
    }

    /**
     * Gets a given annotation node from the {@link ClassNode} passed as first argument.
     *
     * @param classNode the class node annotated with the annotation we're looking for
     * @param annotationType the annotation class node
     * @return the annotation type if found, null otherwise
     * @since 0.1.0
     */
    public AnnotationNode getAnnotationFrom(ClassNode classNode, ClassNode annotationType) {
        List<AnnotationNode> list = classNode.getAnnotations(annotationType);

        return list != null && !list.isEmpty() ? first(list) : null;
    }

    /**
     * Returns true if the classNode passed as first argument is of type `clazz` or that class implements
     * `parent`, false otherwise
     *
     * @param child the {@link Class}  we are checking
     * @param parent the {@link Class} we are testing against
     * @return true if the classNode is of type `clazz`
     * @since 0.1.0
     */
    public Boolean isOrImplements(Class child, Class parent) {
        return isOrImplements(ClassHelper.make(child, false), parent);
    }

    /**
     * Returns true if the classNode passed as first argument is of type `clazz` or that class implements
     * `parent`, false otherwise
     *
     * @param child the {@link ClassNode}  we are checking
     * @param parent the {@link Class}  we are testing against
     * @return true if the classNode is of type `parent`
     * @since 0.1.0
     */
    public Boolean isOrImplements(ClassNode child, Class parent) {
        return GeneralUtils.isOrImplements(child, ClassHelper.make(parent,false));
    }

    /**
     * Returns true if the classNode passed as first argument is of type `clazz` or that class implements
     * `parent`, false otherwise
     *
     * @param child the {@link ClassNode}  we are checking
     * @param parent the qualified name of the {@link Class} we are testing against
     * @return true if the classNode is of type `clazz`
     * @since 0.1.0
     */
    public Boolean isOrImplements(ClassNode child, String parent) {
        return GeneralUtils.isOrImplements(child, ClassHelper.make(parent));
    }

    /**
     * Returns true if the classNode passed as first argument is of type `clazz` or that class extends
     * the other class, false otherwise
     *
     * @param child the {@link ClassNode}  we are checking
     * @param parent the {@link Class}  we are testing against
     * @return true if the classNode is of type `parent`
     * @since 0.1.0
     */
    public Boolean isOrExtends(ClassNode child, Class parent) {
        ClassNode extendedType = ClassHelper.make(parent,false);

        return isOrExtends(child, extendedType);
    }

    /**
     * Returns true if the classNode passed as first argument is of type `clazz` or that class extends
     * the other class, false otherwise
     *
     * @param child the {@link ClassNode}  we are checking
     * @param parent the {@link ClassNode}  we are testing against
     * @return true if the classNode is of type `parent`
     * @since 0.1.0
     */
    public Boolean isOrExtends(ClassNode child, ClassNode parent) {
        return child.equals(parent) || child.isDerivedFrom(parent);
    }

    /**
     * Returns true if the classNode passed as first argument is of type `clazz` or that class extends
     * the other class, false otherwise
     *
     * @param child the {@link ClassNode}  we are checking
     * @param parent the qualified name of the class we are testing against
     * @return true if the classNode is of type `parent`
     * @since 0.1.0
     */
    public Boolean isOrExtends(ClassNode child, String parent) {
        return child.equals(parent) || child.isDerivedFrom(ClassHelper.make(parent));
    }

    /**
     * Given a {@link MethodCallExpression} it returns a list of arguments
     *
     * @param methodCallExpression a method call we want the arguments from
     * @return a list of expressions within a {@link ArgumentListExpression}
     * @since 0.1.3
     */
    public ArgumentListExpression getArgs(final MethodCallExpression methodCallExpression) {
        return (ArgumentListExpression) methodCallExpression.getArguments();
    }

    /**
     * Return the first element of the {@link ArgumentListExpression}
     * passed as parameters as the expected type.
     *
     * @param args the list of arguments
     * @param asType the expected type
     * @return the first argument casted as the expected type
     * @since 0.1.3
     */
    public <U extends Expression> U getFirstArgumentAs(final ArgumentListExpression args, final Class<U> asType) {
        return asType.cast(first(args.getExpressions()));
    }

    /**
     * Return the last element of the {@link ArgumentListExpression}
     * passed as parameters as the expected type.
     *
     * @param args the list of arguments
     * @param asType the expected type
     * @return the last argument casted as the expected type
     * @since 0.1.3
     */
    public <U extends Expression> U getLastArgumentAs(final ArgumentListExpression args, final Class<U> asType) {
        return asType.cast(last(args.getExpressions()));
    }
}
