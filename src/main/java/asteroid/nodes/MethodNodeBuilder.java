package asteroid.nodes;

import asteroid.A;

import java.util.List;
import java.util.ArrayList;

import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.stmt.Statement;

/**
 * Builder to create instance of type {@link MethodNode}
 *
 * @since 0.1.0
 */
public class MethodNodeBuilder {

    private final String name;
    private int modifiers;
    private Statement code;
    private ClassNode returnType;
    private List<Parameter> parameters = new ArrayList();
    private List<ClassNode> exceptions = new ArrayList();

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

    public MethodNodeBuilder code(Statement code) {
        this.code = code;
        return this;
    }

    public MethodNode build() {
        MethodNode methodNode =
            new MethodNode(name,
                           modifiers,
                           returnType,
                           (Parameter[]) parameters.toArray(new Parameter[parameters.size()]),
                           (ClassNode[]) exceptions.toArray(new ClassNode[exceptions.size()]),
                           code);

        return methodNode;
    }

}
