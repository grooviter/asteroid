package asteroid;

import asteroid.nodes.ClassNodeBuilder;
import asteroid.nodes.AnnotationNodeBuilder;
import asteroid.nodes.GenericsTypeBuilder;
import asteroid.nodes.ConstructorNodeBuilder;
import asteroid.nodes.MethodNodeBuilder;
import asteroid.nodes.ParameterNodeBuilder;
import asteroid.nodes.PropertyNodeBuilder;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.GenericsType;

/**
 * This class provides access to different builders for creating AST nodes.
 *
 * @since 0.1.0
 */
public final class Nodes {

    /**
     * Creates an instance of {@link AnnotationNodeBuilder}
     *
     * @param annotationClazz the annotation type
     * @return an instance of {@link AnnotationNodeBuilder}
     * @since 0.1.0
     */
    public AnnotationNodeBuilder annotation(final Class annotationClazz) {
       return AnnotationNodeBuilder.annotation(annotationClazz);
    }

    /**
     * Creates an instance of {@link ClassNodeBuilder}
     *
     * @param type the class type
     * @return an instance of {@link ClassNodeBuilder}
     * @since 0.1.0
     */
    public ClassNodeBuilder clazz(final Class type) {
        return ClassNodeBuilder.clazz(type);
    }

    /**
     * Creates an instance of {@link ClassNodeBuilder}
     *
     * @param qualifiedName the class full qualified name
     * @return and instance of {@link ClassNodeBuilder}
     * @since 0.1.0
     */
    public ClassNodeBuilder clazz(final String qualifiedName) {
        return ClassNodeBuilder.clazz(qualifiedName);
    }

    /**
     * Creates an instance of {@link ClassNodeBuilder}
     *
     * @param type the class type
     * @param generics related generics
     * @return and instance of {@link ClassNodeBuilder}
     * @since 0.1.0
     */
    public ClassNodeBuilder clazzWithGenerics(final Class type, final GenericsType... generics) {
        return ClassNodeBuilder.clazzWithGenerics(type, generics);
    }

    /**
     * Creates an instance of {@link GenericsTypeBuilder}
     *
     * @param classNode the type of the generic type
     * @return an instance of {@link GenericsTypeBuilder}
     * @since 0.1.0
     */
    public GenericsTypeBuilder generics(final ClassNode classNode) {
        return GenericsTypeBuilder.generics(classNode);
    }

    /**
     * Creates an instance of {@link ConstructorNodeBuilder}
     *
     * @param modifiers set of modifiers for the constructor about to build
     * @return an instance of {@link ConstructorNodeBuilder}
     * @since 0.1.0
     */
    public ConstructorNodeBuilder constructor(final int modifiers) {
        return ConstructorNodeBuilder.constructor(modifiers);
    }

    /**
     * Creates an instance of {@link MethodNodeBuilder}
     *
     * @param methodName name of the method to create
     * @return an instance of {@link MethodNodeBuilder}
     * @since 0.1.0
     */
    public MethodNodeBuilder method(final String methodName) {
        return MethodNodeBuilder.method(methodName);
    }

    /**
     * Creates an instance of {@link ParameterNodeBuilder}. This
     * builder can create instances of type {@link
     * org.codehaus.groovy.ast.Parameter}
     *
     * @param paramName of the parameter
     * @return an instance of {@link ParameterNodeBuilder}
     * @since 0.1.0
     */
    public ParameterNodeBuilder param(final String paramName) {
        return ParameterNodeBuilder.param(paramName);
    }

    /**
     * Creates an instnace of {@link PropertyNodeBuilder}. This
     * builder can create instances of type {@link
     * org.codehaus.groovy.ast.PropertyNode}
     *
     * @param propertyName the name of the property
     * @return an instance of {@link PropertyNodeBuilder}
     *
     * @since 0.1.4
     */
    public PropertyNodeBuilder property(final String propertyName) {
        return PropertyNodeBuilder.property(propertyName);
    }
}
