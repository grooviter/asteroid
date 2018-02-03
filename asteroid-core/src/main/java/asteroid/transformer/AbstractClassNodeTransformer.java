package asteroid.transformer;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.SourceUnit;
import groovy.lang.Closure;

/**
 * This {@link Transformer} can be used to transform {@link ClassNode}
 * nodes. To locate the classes you want to transform, a criteria in
 * form of {@link Closure} can be passed in the constructor.
 * The criteria must be a {@link Closure} receiving as unique parameter
 * a {@link ClassNode} and returning a {@link Boolean} value. If the closure
 * returns true, then the transformation will be applied.
 * </br></br>
 * There are some static methods creating some default criterias:
 * </br></br>
 * <ul>
 *    <li>{@link AbstractClassNodeTransformer#byNameContains}</li>
 *    <li>{@link AbstractClassNodeTransformer#byNameStartsWith}</li>
 *    <li>{@link AbstractClassNodeTransformer#byNameEndsWith}</li>
 * </ul>
 *
 * @since 0.2.0
 */
public abstract class AbstractClassNodeTransformer extends AbstractTransformer {

    private final Closure<Boolean> criteria;

    /**
     * Every instance needs the source unit and the name of the class
     * it's going to transform.
     *
     * To find classes eligible to be transformed by this {@link Transformer}
     * a {@link Closure} returning a boolean is used as a criteria.
     *
     * You can create your own criteria or use one of the static
     * methods present in this class. These methods will create a
     * pre-defined criteria.
     *
     * The {@link Closure} used as a criteria will receive a {@link
     * ClassNode} and return a {@link Boolean} value. If the closure
     * returns true then the transformation will be apply, if not the
     * {@link ClassNode} will be discarded.
     *
     * @param sourceUnit Needed to apply scope
     * @param criteria used to locate target classes
     * look for the qualified class)
     * @since 0.2.0
     * @see AbstractClassNodeTransformer#byNameContains
     */
    public AbstractClassNodeTransformer(final SourceUnit sourceUnit, final Closure<Boolean> criteria) {
        super(sourceUnit);
        this.criteria = criteria;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitClass(final ClassNode classNode) {
        if (classNode == null || !this.criteria.call(classNode)) {
            return;
        }

        transformClass(classNode);
    }

    /**
     * Within this method developer could modify the {@link ClassNode}
     * instance.
     *
     * @param classNode the {@link ClassNode}  you want to transform
     * @since 0.2.0
     */
    public abstract void transformClass(final ClassNode classNode);

}
