package asteroid.utils;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.first;

import java.util.List;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.tools.GeneralUtils;

/**
 * Utility classes to deal with {@link ClassNode} instances
 *
 * @since 0.1.4
 * @see asteroid.Utils
 */
public final class ClassNodeUtils {

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
     * Makes the {@link ClassNode} to implement the interfaces passed
     * as arguments
     *
     * @param classNode
     * @param interfaces the interfaces we want the class node to be
     * implementing
     * @since 0.1.4
     */
    public void addInterfaces(final ClassNode classNode, Class... interfaces) {
        for (Class clazz : interfaces) {
            ClassNode nextInterface = ClassHelper.make(clazz, false);
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
    public List<FieldNode> getInstancePropertyFields(ClassNode classNode) {
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
     * @since 0.1.4
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
     * @since 0.1.4
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
     * @since 0.1.4
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
     * @since 0.1.4
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
     * @since 0.1.4
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
     * @since 0.1.4
     */
    public Boolean isOrExtends(ClassNode child, String parent) {
        return child.equals(parent) || child.isDerivedFrom(ClassHelper.make(parent));
    }
}
