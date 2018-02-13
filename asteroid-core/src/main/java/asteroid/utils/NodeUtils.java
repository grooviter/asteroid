package asteroid.utils;

import static org.codehaus.groovy.runtime.StringGroovyMethods.take;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.find;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.first;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.last;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

import groovy.lang.Closure;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.control.CompilePhase;
import asteroid.A;
import asteroid.utils.StatementUtils.Group;

/**
 * General utility methods to deal with {@link ASTNode} instances
 *
 * @since 0.1.4
 * @see asteroid.Utils
 */
public class NodeUtils {

    public static final String ANNOTATION_VALUE = "value";

    /**
     * Adds checks to the method node passed as parameter
     *
     * @param methodNode the node we want to add the checks to
     * @since 0.3.0
     */
    public void addCheckTo(final MethodNode methodNode) {
        final BlockStatement blockStmt   = A.UTIL.NODE.getCodeBlock(methodNode);
        final List<Group> groups         = A.UTIL.STMT.groupStatementsByLabel(blockStmt);
        final List<Statement> statements = A.UTIL.STMT.applyToStatementsByLabelFlatten(groups, getMappings());

        // #TODO it will remove to enforce the use of checks
        if (!groups.isEmpty()) {
            methodNode.setCode(A.STMT.blockS(statements));
        }
    }

    private Map<String, Closure<Statement>> getMappings() {
        final Map<String, Closure<Statement>> mappings = new HashMap<String, Closure<Statement>>();
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

    /**
     * Returns the first node of the list passed as parameter with the expected type T
     *
     * @param nodes a list of nodes
     * @param clazz the type of the expected value
     * @param <T> the expected type
     * @return a node with type T
     * @since 0.3.0
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
     * @since 0.3.0
     */
    public <T> T getLastNodeAs(final ASTNode[] nodes, final Class<T> clazz) {
        return (T) last(nodes);
    }

    /**
     * Returns the {@link String} representation of the member "value" of the annotation
     * passed as parameter
     *
     * @param annotationNode The node we want the value from
     * @return the value of the member "value" as a {@link String}
     * @since 0.3.0
     */
    public String getStringValue(final AnnotationNode annotationNode) {
        final Expression expr = annotationNode.getMember(ANNOTATION_VALUE);

        if (expr != null) {
            return expr.getText();
        }

        return null;
    }

    /**
     * Returns the value of a given annotation member. The value is casted to the type passed as second
     * argument.
     *
     * @param annotationNode The node we want the member value from
     * @param clazz the type of value expected
     * @param <T> The expected return type
     * @return an instance of type T
     * @since 0.3.0
     */
    public <T> T get(final AnnotationNode annotationNode, final Class<T> clazz) {
        final Object value = resolveValueFrom(annotationNode.getMember(ANNOTATION_VALUE));

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
     * @since 0.3.0
     */
    public <T> T get(final AnnotationNode annotationNode, final String name, final Class<T> clazz) {
        final Object value = resolveValueFrom(annotationNode.getMember(name));

        return clazz.cast(value);
    }

   private Object resolveValueFrom(final Expression expression) {
       if (expression == null) {
           return null;
       }
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
     * Adds the property to the class node passed as first argument
     *
     * @param classNode the class we want to add the property to
     * @param propertyNode the property we want to add
     * @since 0.3.0
     */
    public void addProperty(final ClassNode classNode, final PropertyNode propertyNode) {
        classNode.addProperty(propertyNode);
    }

    /**
     * Adds a property to the class node passed as first argument only
     * if it wasn't present in the first place
     *
     * @param classNode the class we want to add the property to
     * @param propertyNode the property we want to add
     * @since 0.3.0
     */
    public void addPropertyIfNotPresent(final ClassNode classNode, final PropertyNode propertyNode) {
        if (!classNode.hasProperty(propertyNode.getName())) {
            classNode.addProperty(propertyNode);
        }
    }

    /**
     * Adds the method to the class node passed as first argument
     *
     * @param classNode the class we want to add the method to
     * @param methodNode the method we want to add
     * @since 0.3.0
     */
    public void addMethod(final ClassNode classNode, final MethodNode methodNode) {
        classNode.addMethod(methodNode);
    }

    /**
     * Adds a method to the class node passed as first argument only
     * if it wasn't present in the first place
     *
     * @param classNode the class we want to add the method to
     * @param methodNode the method we want to add
     * @since 0.3.0
     */
    public void addMethodIfNotPresent(final ClassNode classNode, final MethodNode methodNode) {
        if (!classNode.hasMethod(methodNode.getName(), methodNode.getParameters())) {
            classNode.addMethod(methodNode);
        }
    }

    /**
     * Makes the {@link ClassNode} to implement the interfaces passed
     * as arguments
     * <br><br>
     * <b>IMPORTANT</b>: Dont use this method at any {@link CompilePhase}
     * before SEMANTIC_ANALYSIS. Classes may have not been set at this
     * point.
     *
     * @param classNode The class we want to add the interfaces to
     * @param interfaces the interfaces we want the class node to be
     * implementing
     * @since 0.3.0
     */
    public void addInterfaces(final ClassNode classNode, final Class... interfaces) {
        for (final Class clazz : interfaces) {
            final ClassNode nextInterface = ClassHelper.make(clazz, false);
            classNode.addInterface(nextInterface);
        }
    }

    /**
     * Makes the {@link ClassNode} to implement the interfaces passed
     * as arguments.
     *
     * @param classNode the {@link ClassNode} we want to implement
     * certain interfaces
     * @param interfaces the interfaces we want our {@link ClassNode}
     * to implement
     * @since 0.3.0
     */
    public void addInterfaces(final ClassNode classNode, final ClassNode... interfaces) {
        for (final ClassNode nextInterface : interfaces) {
            classNode.addInterface(nextInterface);
        }
    }

    /**
     * Returns all properties from a given {@link ClassNode} passed as
     * argument
     *
     * @param classNode the {@link ClassNode} we want its properties from
     * @return a list of the properties ({@link FieldNode}) of a given
     * {@link ClassNode}
     * @since 0.3.0
     */
    public List<FieldNode> getInstancePropertyFields(final ClassNode classNode) {
        return GeneralUtils.getInstancePropertyFields(classNode);
    }

    /**
     * Gets a given annotation node from the {@link ClassNode} passed as first argument.
     *
     * @param classNode the class node annotated with the annotation we're looking for
     * @param annotationType the annotation class node
     * @return the annotation type if found, null otherwise
     * @since 0.3.0
     */
    public AnnotationNode getAnnotationFrom(final ClassNode classNode, final ClassNode annotationType) {
        return find(classNode.getAnnotations(annotationType));
    }

    /**
     * Retrieves an annotation with a specific simple name. A simple name stands for
     * the qualified name of the type minus the package name, e.g if we had a type
     * "groovy.transform.ToString" the simple name would be "ToString".
     *
     * @param classNode the class node annotated with the annotation we're looking for
     * @param simpleName the annotation type simple name
     * @return the annotation node if found, null otherwise
     * @since 0.3.0
     */
    public AnnotationNode getAnnotationFrom(final ClassNode classNode, final String simpleName) {
        return find(classNode.getAnnotations(), bySimpleName(simpleName));
    }

    /**
     * It removes the {@link AnnotationNode} from a given {@link AnnotatedNode}
     *
     * @param annotated the {@link AnnotatedNode} to remove the annotation from
     * @param annotation the {@link AnnotationNode} you want to remove
     * @since 0.3.0
     */
    public void removeAnnotation(final AnnotatedNode annotated, final AnnotationNode annotation) {
        annotated.getAnnotations().remove(annotation);
    }

    private Closure<Boolean> bySimpleName(final String annotationName) {
        return new Closure(null) {
            public boolean doCall(final AnnotationNode node) {
                return node.getClassNode()
                    .getNameWithoutPackage()
                    .equals(annotationName);
            }
        };
    }

    /**
     * Returns whether if the {@link ClassNode} passed as first
     * parameter has any field with the type matching the qualified
     * name passed as second parameter.
     *
     * @param node The node under test
     * @param qualifiedName The qualified name of the type
     * @return true if there is any field with the given type, false
     * otherwise
     * @since 0.3.0
     */
    public Boolean hasFieldOfType(final ClassNode node, final String qualifiedName) {
        final List<FieldNode> nodeFields = node.getFields();
        final Closure<Boolean> predicate = new Closure<Boolean>(null){
                public Boolean doCall(final FieldNode fieldNode) {
                    return isOrExtends(fieldNode.getType(), qualifiedName);
                }
            };

        return any(nodeFields, predicate);
    }

    /**
     * Returns true if the classNode passed as first argument is of type `clazz` or that class implements
     * `parent`, false otherwise
     *
     * @param child the {@link Class}  we are checking
     * @param parent the {@link Class} we are testing against
     * @return true if the classNode is of type `clazz`
     * @since 0.3.0
     */
    public Boolean isOrImplements(final Class child, final Class parent) {
        return isOrImplements(ClassHelper.make(child, false), parent);
    }

    /**
     * Returns true if the classNode passed as first argument is of type `clazz` or that class implements
     * `parent`, false otherwise
     *
     * @param child the {@link ClassNode}  we are checking
     * @param parent the {@link Class}  we are testing against
     * @return true if the classNode is of type `parent`
     * @since 0.3.0
     */
    public Boolean isOrImplements(final ClassNode child, final Class parent) {
        return GeneralUtils.isOrImplements(child, ClassHelper.make(parent,false));
    }

    /**
     * Returns true if the classNode passed as first argument is of type `clazz` or that class implements
     * `parent`, false otherwise
     *
     * @param child the {@link ClassNode}  we are checking
     * @param parent the qualified name of the {@link Class} we are testing against
     * @return true if the classNode is of type `clazz`
     * @since 0.3.0
     */
    public Boolean isOrImplements(final ClassNode child, final String parent) {
        return GeneralUtils.isOrImplements(child, ClassHelper.make(parent));
    }

    /**
     * Returns true if the classNode passed as first argument is of type `clazz` or that class extends
     * the other class, false otherwise
     *
     * @param child the {@link ClassNode}  we are checking
     * @param parent the {@link Class}  we are testing against
     * @return true if the classNode is of type `parent`
     * @since 0.3.0
     */
    public Boolean isOrExtends(final ClassNode child, final Class parent) {
        final ClassNode extendedType = ClassHelper.make(parent, false);

        return isOrExtends(child, extendedType);
    }

    /**
     * Returns true if the classNode passed as first argument is of type `clazz` or that class extends
     * the other class, false otherwise
     *
     * @param child the {@link ClassNode}  we are checking
     * @param parent the {@link ClassNode}  we are testing against
     * @return true if the classNode is of type `parent`
     * @since 0.3.0
     */
    public Boolean isOrExtends(final ClassNode child, final ClassNode parent) {
        return child.equals(parent) || child.isDerivedFrom(parent);
    }

    /**
     * Returns true if the classNode passed as first argument is of type `clazz` or that class extends
     * the other class, false otherwise
     *
     * @param child the {@link ClassNode}  we are checking
     * @param parent the qualified name of the class we are testing against
     * @return true if the classNode is of type `parent`
     * @since 0.3.0
     */
    public Boolean isOrExtends(final ClassNode child, final String parent) {
        return child.equals(parent) || child.isDerivedFrom(ClassHelper.make(parent));
    }

    /**
     * Returns true if the classNode passed as first argument is of type `clazz` or that class extends
     * the other class, false otherwise. It's unsafe because it tries to compare also the super class
     * simple name. So two classes having the same name even in different packages will return true.
     *
     * @param child the {@link ClassNode}  we are checking
     * @param parent the {@link ClassNode}  we are testing against
     * @return true if the classNode is of type `parent`
     * @since 0.3.0
     */
    public Boolean isOrExtendsUnsafe(final ClassNode child, final ClassNode parent) {
        return child.equals(parent)        ||
               child.isDerivedFrom(parent) ||
               child.getSuperClass()
                   .getNameWithoutPackage()
                   .equals(parent.getNameWithoutPackage());
    }

    /**
     * Returns the first {@link MethodNode} found with a given name in
     * a specific {@link ClassNode}
     *
     * @param classNode the {@link ClassNode} the method should be found
     * @param methodName the method name
     * @return an instance of {@link MethodNode} if found
     * @since 0.3.0
     */
    public MethodNode findMethodByName(final ClassNode classNode, final String methodName) {
        return first(findAllMethodByName(classNode, methodName));
    }

    /**
     * Returns all {@link MethodNode} found with a given name in a
     * specific {@link ClassNode}
     *
     * @param classNode the {@link ClassNode} the method should be found
     * @param methodName the method name
     * @return an instance of {@link MethodNode} if found
     * @since 0.3.0
     */
    public List<MethodNode> findAllMethodByName(final ClassNode classNode, final String methodName) {
        return classNode.getMethods(methodName);
    }

    /**
     * Adds an import to the {@link ModuleNode} containing the {@link
     * ClassNode} passed as first argument.
     *
     * @param classNode the {@link ClassNode}  where the import will be added
     * @param clazz the type {@link Class} of the import
     * @since 0.3.0
     */
    public void addImport(final ClassNode classNode, final Class clazz) {
        classNode.getModule().addImport(clazz.getSimpleName(), ClassHelper.make(clazz, false));
    }

    /**
     * Adds an import to the {@link ModuleNode} containing the {@link
     * ClassNode} passed as first argument.
     *
     * @param classNode the {@link ClassNode}  where the import will be added
     * @param clazz the string representing the qualified class of the import
     * @since 0.3.0
     */
    public void addImport(final ClassNode classNode, final String clazz) {
        classNode.getModule().addImport(getClassNameFromString(clazz), ClassHelper.make(clazz));
    }

    private String getClassNameFromString(final String clazz) {
        if (clazz == null || clazz.isEmpty()) {
            return clazz;
        }

        final int clazzPackage  = clazz.lastIndexOf('.');
        final CharSequence name = take(clazz, clazzPackage);

        return name.toString();
    }

    /**
     * Adds an import to the {@link ModuleNode} containing the {@link
     * ClassNode} passed as first argument.
     *
     * @param classNode the {@link ClassNode}  where the import will be added
     * @param clazz the type {@link Class} of the import
     * @param alias an alias to avoid class collisions
     * @since 0.3.0
     */
    public void addImport(final ClassNode classNode, final Class clazz, final String alias) {
        classNode.getModule().addImport(alias, ClassHelper.make(clazz, false));
    }

    /**
     * Adds an import to the {@link ModuleNode} containing the {@link
     * ClassNode} passed as first argument.
     *
     * @param classNode the {@link ClassNode}  where the import will be added
     * @param clazz the string representing the qualified class of the import
     * @param alias an alias to avoid class collisions
     * @since 0.3.0
     */
    public void addImport(final ClassNode classNode, final String clazz, final String alias) {
        classNode.getModule().addImport(alias, ClassHelper.make(clazz));
    }

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
