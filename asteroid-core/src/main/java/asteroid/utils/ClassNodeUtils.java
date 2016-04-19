package asteroid.utils;

import static org.codehaus.groovy.runtime.StringGroovyMethods.take;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.find;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.first;

import java.util.List;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.tools.GeneralUtils;

import groovy.lang.Closure;

/**
 * Utility classes to deal with {@link ClassNode} instances
 *
 * @since 0.1.4
 * @see asteroid.Utils
 */
public final class ClassNodeUtils {

    /**
     * Adds the property to the class node passed as first argument
     *
     * @param classNode the class we want to add the property to
     * @param propertyNode the property we want to add
     * @since 0.1.4
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
     * @since 0.1.4
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
     * @since 0.1.4
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
     * @since 0.1.4
     */
    public void addMethodIfNotPresent(final ClassNode classNode, final MethodNode methodNode) {
        if (!classNode.hasMethod(methodNode.getName(), methodNode.getParameters())) {
            classNode.addMethod(methodNode);
        }
    }

    /**
     * Makes the {@link ClassNode} to implement the interfaces passed
     * as arguments
     *
     * @param classNode
     * @param interfaces the interfaces we want the class node to be
     * implementing
     * @since 0.1.4
     */
    public void addInterfaces(final ClassNode classNode, final Class... interfaces) {
        for (final Class clazz : interfaces) {
            final ClassNode nextInterface = ClassHelper.make(clazz, false);
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
     * @since 0.1.4
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
     * @since 0.1.4
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
     * @since 0.1.6
     */
    public AnnotationNode getAnnotationFrom(final ClassNode classNode, final String simpleName) {
        return find(classNode.getAnnotations(), byName(simpleName));
    }

    /**
     * It removes the {@link AnnotationNode} from a given {@link AnnotatedNode}
     *
     * @param annotated the {@link AnnotatedNode} to remove the annotation from
     * @param annotation the {@link AnnotationNode} you want to remove
     * @since 0.1.7
     */
    public void removeAnnotation(final AnnotatedNode annotated, final AnnotationNode annotation) {
        annotated.getAnnotations().remove(annotation);
    }

    private Closure<Boolean> byName(final String annotationName) {
        return new Closure(null) {
            public boolean doCall(final AnnotationNode node) {
                return node.getClassNode().getName().equals(annotationName);
            }
        };
    }

    /**
     * Returns true if the classNode passed as first argument is of type `clazz` or that class implements
     * `parent`, false otherwise
     *
     * @param child the {@link Class}  we are checking
     * @param parent the {@link Class} we are testing against
     * @return true if the classNode is of type `clazz`
     * @since 0.1.4
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
     * @since 0.1.4
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
     * @since 0.1.4
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
     * @since 0.1.4
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
     * @since 0.1.4
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
     * @since 0.1.4
     */
    public Boolean isOrExtends(final ClassNode child, final String parent) {
        return child.equals(parent) || child.isDerivedFrom(ClassHelper.make(parent));
    }

    /**
     * Returns the first {@link MethodNode} found with a given name in
     * a specific {@link ClassNode}
     *
     * @param classNode the {@link ClassNode} the method should be found
     * @param methodName the method name
     * @return an instance of {@link MethodNode} if found
     * @since 0.1.5
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
     * @since 0.1.5
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
     * @since 0.1.6
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
     * @since 0.1.6
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
     * @since 0.1.6
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
     * @since 0.1.6
     */
    public void addImport(final ClassNode classNode, final String clazz, final String alias) {
        classNode.getModule().addImport(alias, ClassHelper.make(clazz));
    }
}
