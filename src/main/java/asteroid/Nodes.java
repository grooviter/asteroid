package asteroid;

import asteroid.nodes.AnnotationNodeBuilder;
import asteroid.nodes.ClassNodeBuilder;
import asteroid.nodes.ConstructorNodeBuilder;
import asteroid.nodes.GenericsTypeBuilder;
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
     */
    public AnnotationNodeBuilder annotation(Class annotationClazz) {
       return AnnotationNodeBuilder.annotation(annotationClazz);
    }

    /**
     * Creates an instance of {@link ClassNodeBuilder}
     *
     * @param type the class type
     * @return an instance of {@link ClassNodeBuilder}
     */
    public ClassNodeBuilder clazz(Class type) {
        return ClassNodeBuilder.clazz(type);
    }

    /**
     * Creates an instance of {@link ClassNodeBuilder}
     *
     * @param fullyQualifiedName the class full qualified name
     * @return and instance of {@link ClassNodeBuilder}
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
     */
    public ClassNodeBuilder clazzWithGenerics(Class type, GenericsType... generics) {
        return ClassNodeBuilder.clazzWithGenerics(type, generics);
    }

    /**
     * Creates an instance of {@link GenericsTypeBuilder}
     *
     * @param classNode the type of the generic type
     * @return an instance of {@link GenericsTypeBuilder}
     */
    public GenericsTypeBuilder generics(ClassNode classNode) {
        return GenericsTypeBuilder.generics(classNode);
    }

    /**
     * Creates an instance of {@link ConstructorNodeBuilder}
     *
     * @param modifiers set of modifiers for the constructor about to build
     * @return an instance of {@link ConstructorNodeBuilder}
     */
    public ConstructorNodeBuilder constructor(int modifiers) {
        return ConstructorNodeBuilder.constructor(modifiers);
    }
}
