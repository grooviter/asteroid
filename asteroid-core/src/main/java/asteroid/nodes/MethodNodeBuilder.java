package asteroid.nodes;

import asteroid.A;

import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.stmt.Statement;

/**
 * Builder to create instance of type {@link MethodNode}
 *
 * @since 0.1.0
 */
final public class MethodNodeBuilder {

    private final String name;
    private int modifiers;
    private Statement code;
    private ClassNode returnType;
    private Parameter[] parameters = new Parameter[0];
    private ClassNode[] exceptions = new ClassNode[0];

    private MethodNodeBuilder(String name) {
        this.name = name;
    }

    public static MethodNodeBuilder method(String name) {
        return new MethodNodeBuilder(name);
    }

    public MethodNodeBuilder returnType(Class returnType) {
        this.returnType = A.NODES.clazz(returnType).build();
        return this;
    }

    public MethodNodeBuilder modifiers(int modifiers) {
        this.modifiers = modifiers;
        return this;
    }

    public MethodNodeBuilder parameters(Parameter... parameters) {
        this.parameters = parameters;
        return this;
    }

    public MethodNodeBuilder exceptions(ClassNode... exceptions) {
        this.exceptions = exceptions;
        return this;
    }

    public MethodNodeBuilder code(Statement code) {
        this.code = code;
        return this;
    }

    public MethodNode build() {
        MethodNode methodNode =
            new MethodNode(name,
                           modifiers,
                           returnType,
                           parameters,
                           exceptions,
                           code);

        return methodNode;
    }

}
