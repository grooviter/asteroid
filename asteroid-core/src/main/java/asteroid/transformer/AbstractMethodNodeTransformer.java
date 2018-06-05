package asteroid.transformer;

import org.codehaus.groovy.ast.MethodNode;

import org.codehaus.groovy.control.SourceUnit;
import groovy.lang.Closure;

/**
 * This {@link Transformer} can be used to transform {@link MethodNode}
 * nodes. To locate the classes you want to transform, a criteria in
 * form of {@link Closure} can be passed in the constructor.
 * The criteria must be a {@link Closure} receiving as unique parameter
 * a {@link MethodNode} and returning a {@link Boolean} value. If the closure
 * returns true, then the transformation will be applied.
 * </br></br>
 * There are some static methods creating some default criterias:
 * </br></br>
 * <ul>
 *    <li>{@link asteroid.Criterias#byMethodNodeNameContains(String)}</li>
 *    <li>{@link asteroid.Criterias#byMethodNodeNameStartsWith(String)}</li>
 *    <li>{@link asteroid.Criterias#byMethodNodeNameEndsWith(String)}</li>
 * </ul>
 *
 * @since 0.2.0
 */
public abstract class AbstractMethodNodeTransformer extends AbstractTransformer {

    private final Closure<Boolean> criteria;

    /**
     * Every instance needs the source unit and the name of the class
     * it's going to transform.
     *
     * To find methods eligible to be transformed by this {@link Transformer}
     * a {@link Closure} returning a boolean is used as a criteria.
     *
     * You can create your own criteria or use one of the static
     * methods present in this class. These methods will create a
     * pre-defined criteria.
     *
     * The {@link Closure} used as a criteria will receive a {@link
     * MethodNode} and return a {@link Boolean} value. If the closure
     * returns true then the transformation will be apply, if not the
     * {@link MethodNode} will be discarded.
     *
     * @deprecated
     * @param sourceUnit Needed to apply scope
     * @param criteria used to locate target classes
     * look for the qualified method)
     * @since 0.2.0
     * @see asteroid.Criterias#byMethodNodeNameContains(String)
     */
    public AbstractMethodNodeTransformer(final SourceUnit sourceUnit, final Closure<Boolean> criteria) {
        super(sourceUnit);
        this.criteria = criteria;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitMethod(final MethodNode methodNode) {
        if (methodNode == null || !this.criteria.call(methodNode)) {
            return;
        }

        transformMethod(methodNode);
    }

    /**
     * Within this method developer could modify the {@link MethodNode}
     * instance.
     *
     * @param methodNode the {@link MethodNode}  you want to transform
     * @since 0.2.0
     */
    public abstract void transformMethod(MethodNode methodNode);

}
