package asteroid;

import asteroid.nodes.AnnotationNodeBuilder;
import asteroid.nodes.ClassNodeBuilder;
import asteroid.nodes.ConstructorNodeBuilder;
import asteroid.nodes.GenericsTypeBuilder;
import asteroid.nodes.MethodNodeBuilder;
import asteroid.nodes.ParameterNodeBuilder;
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
    public AnnotationNodeBuilder annotation(Class annotationClazz) {
       return AnnotationNodeBuilder.annotation(annotationClazz);
    }

    /**
     * Creates an instance of {@link ClassNodeBuilder}
     *
     * @param type the class type
     * @return an instance of {@link ClassNodeBuilder}
     * @since 0.1.0
     */
    public ClassNodeBuilder clazz(Class type) {
        return ClassNodeBuilder.clazz(type);
    }

    /**
     * Creates an instance of {@link ClassNodeBuilder}
     *
     * @param fullyQualifiedName the class full qualified name
     * @return and instance of {@link ClassNodeBuilder}
     * @since 0.1.0
     */
    public ClassNodeBuilder clazz(String fullyQualifiedName) {
        return ClassNodeBuilder.clazz(fullyQualifiedName);
    }

    /**
     * Creates an instance of {@link ClassNodeBuilder}
     *
     * @param type the class type
     * @param generics related generics
     * @return and instance of {@link ClassNodeBuilder}
     * @since 0.1.0
     */
    public ClassNodeBuilder clazzWithGenerics(Class type, GenericsType... generics) {
        return ClassNodeBuilder.clazzWithGenerics(type, generics);
    }

    /**
     * Creates an instance of {@link GenericsTypeBuilder}
     *
     * @param classNode the type of the generic type
     * @return an instance of {@link GenericsTypeBuilder}
     * @since 0.1.0
     */
    public GenericsTypeBuilder generics(ClassNode classNode) {
        return GenericsTypeBuilder.generics(classNode);
    }

    /**
     * Creates an instance of {@link ConstructorNodeBuilder}
     *
     * @param modifiers set of modifiers for the constructor about to build
     * @return an instance of {@link ConstructorNodeBuilder}
     * @since 0.1.0
     */
    public ConstructorNodeBuilder constructor(int modifiers) {
        return ConstructorNodeBuilder.constructor(modifiers);
    }

    /**
     * Creates an instance of {@link MethodNodeBuilder}
     *
     * @param methodName name of the method to create
     * @return an instance of {@link MethodNodeBuilder}
     * @since 0.1.0
     */
    public MethodNodeBuilder method(String methodName) {
        return MethodNodeBuilder.method(methodName);
    }

    /**
     * Creates an instance of {@link ParameterNodeBuilder}. This builder can create instances
     * of type {@link org.codehaus.groovy.ast.Parameter}
     *
     * @param paramName of the parameter
     * @return an instance of {@link ParameterNodeBuilder}
     * @since 0.1.0
     */
    public ParameterNodeBuilder param(String paramName) {
        return ParameterNodeBuilder.param(paramName);
    }
}
