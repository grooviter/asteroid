package asteroid.global;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.newInstance;

import java.util.List;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;

/**
 * This class applies all transformers provided by the method
 * getTransformers in order to all class nodes present in a given
 * source unit instance.
 *
 * This way you should think of an instance of {@link
 * GlobalTransformationImpl} as a set of {@link
 * ClassCodeVisitorSupport} instances.
 *
 * @since 0.1.1
 * @see ClassCodeVisitorSupport
 */
abstract class GlobalTransformationImpl extends AbstractASTTransformation {

    /**
     * {@inheritDoc}
     *
     * @since 0.1.1
     */
    @SuppressWarnings("UnusedMethodParameter")
    public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        List<ClassNode> classNodeList = (List<ClassNode>) collect(sourceUnit.getAST().getClasses());

        for (ClassNode clazzNode : classNodeList) {
            for (Class<? extends ClassCodeVisitorSupport> clazz : getTransformers()) {
                newInstance(clazz, new Object[] { sourceUnit })
                    .visitClass(clazzNode);
            }
        }
    }

    /**
     * This method should return a list of visitor classes {@link
     * Class} instances applied globally by this AST.
     *
     * @return all transformers that will be applied globally by this
     * AST transformation
     * @since 0.1.1
     * @see ClassCodeVisitorSupport
     */
    public abstract List<Class<? extends ClassCodeVisitorSupport>> getTransformers();

}
