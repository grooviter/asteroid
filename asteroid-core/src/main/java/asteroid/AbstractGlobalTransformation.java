package asteroid;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.newInstance;

import java.util.List;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;

import asteroid.transformer.Transformer;

/**
 * This class applies all transformers provided by the method
 * getTransformers in order to all class nodes present in a given
 * source unit instance.
 *
 * This way you should think of an instance of {@link
 * AbstractGlobalTransformation} as a set of {@link Transformer}
 * instances.
 *
 * @since 0.2.0
 * @see Transformer
 */
public abstract class AbstractGlobalTransformation extends AbstractASTTransformation {

    /**
     * {@inheritDoc}
     *
     * @since 0.2.0
     */
    @SuppressWarnings({"PMD.UnusedMethodParameter", "PMD.AvoidInstantiatingObjectsInLoops"})
    public void visit(final ASTNode[] nodes, final SourceUnit sourceUnit) {
        final List<ClassNode> classNodeList = (List<ClassNode>) collect(sourceUnit.getAST().getClasses());

        for (final ClassNode clazzNode : classNodeList) {
            for (final Class<? extends Transformer> clazz : getTransformers()) {
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
     * @since 0.2.0
     * @see Transformer
     */
    public abstract List<Class<? extends Transformer>> getTransformers();
}
