package asteroid.nodes;

import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.tools.GenericsUtils;

/**
 * Builder to create instances of type {@link ClassNode}
 *
 * @since 0.1.0
 */
public class ClassNodeBuilder {

    private final ClassNode classNode;

    private ClassNodeBuilder(Class clazz) {
        classNode = new ClassNode(clazz);
    }

    private ClassNodeBuilder(String fullyQualifiedName) {
        this.classNode = ClassHelper.make(fullyQualifiedName);
    }

    private ClassNodeBuilder(ClassNode classNode) {
        this.classNode = classNode;
    }

    /**
     *
     * @param clazz
     * @return an instance of {@link ClassNodeBuilder}
     */
    public static ClassNodeBuilder clazz(Class<?> clazz) {
        return new ClassNodeBuilder(clazz);
    }

    /**
     *
     * @param fullyQualifiedName
     * @return an instance of {@link ClassNodeBuilder}
     */
    public static ClassNodeBuilder clazz(String fullyQualifiedName) {
        return new ClassNodeBuilder(fullyQualifiedName);
    }

    /**
     *
     * @param clazz
     * @param genericsTypes
     * @return an instance of {@link ClassNodeBuilder}
     */
    public static ClassNodeBuilder clazzWithGenerics(Class<?> clazz, GenericsType... genericsTypes) {
        return new ClassNodeBuilder(GenericsUtils
                .makeClassSafeWithGenerics(
                        ClassHelper.make(clazz),
                        genericsTypes
                ));
    }

    /**
     *
     * @param useGenerics
     * @return an instance of {@link ClassNodeBuilder}
     */
    public ClassNodeBuilder usingGenerics(Boolean useGenerics) {
        this.classNode.setUsingGenerics(useGenerics);
        return this;
    }

    /**
     * @param usePlaceholder
     * @return an instance of {@link ClassNodeBuilder}
     */
    public ClassNodeBuilder genericsPlaceHolder(Boolean usePlaceholder) {
        this.classNode.setGenericsPlaceHolder(usePlaceholder);
        return this;
    }

    /**
     * @param genericsTypes
     * @return an instance of {@link ClassNodeBuilder}
     */
    public ClassNodeBuilder genericsTypes(GenericsType... genericsTypes) {
        this.classNode.setGenericsTypes(genericsTypes);
        return this;
    }

    /**
     *
     * @return an instance of {@link ClassNode}
     */
    public ClassNode build() {
        return this.classNode;
    }

}
