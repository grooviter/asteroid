package asteroid.global;

import org.codehaus.groovy.ast.expr.Expression;
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
 *    <li>{@link MethodNodeTransformer#byNameContains}</li>
 *    <li>{@link MethodNodeTransformer#byNameStartsWith}</li>
 *    <li>{@link MethodNodeTransformer#byNameEndsWith}</li>
 * </ul>
 *
 * @since 0.1.5
 */
public abstract class MethodNodeTransformer extends Transformer {

    private Closure<Boolean> criteria;

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
     * @param sourceUnit Needed to apply scope
     * @param criteria used to locate target classes
     * look for the qualified method)
     * @since 0.1.5
     * @see MethodNodeTransformer#byNameContains
     */
    public MethodNodeTransformer(SourceUnit sourceUnit, Closure<Boolean> criteria) {
        super(sourceUnit);
        this.criteria = criteria;
    }

    /**
     * Criteria to find those methods with a name containing the term
     * passed as parameter
     *
     * @param term the term contained in the {@link MethodNode} name
     * @return a criteria to use in the {@link MethodNodeTransformer} constructor
     * @since 0.1.5
     */
    public static Closure<Boolean> byNameContains(final String term) {
        return new Closure<Boolean>(null) {
            public Boolean doCall(MethodNode node) {
                return node.getName().contains(term);
            }
        };
    }

    /**
     * Criteria to find those methods with a name containing the term
     * passed as parameter at the end.
     *
     * @param term the term at the end of the {@link MethodNode} name
     * @return a criteria to use in the {@link MethodNodeTransformer} constructor
     * @since 0.1.5
     */
    public static Closure<Boolean> byNameEndsWith(final String term) {
        return new Closure<Boolean>(null) {
            public Boolean doCall(MethodNode node) {
                return node.getName().endsWith(term);
            }
        };
    }

    /**
     * Criteria to find those methods with a name containing the term
     * passed as parameter at the beginning.
     *
     * @param term at the beginning of the {@link MethodNode} name
     * @return a criteria to use in the {@link MethodNodeTransformer} constructor
     * @since 0.1.5
     */
    public static Closure<Boolean> byNameStartsWith(final String term) {
        return new Closure<Boolean>(null) {
            public Boolean doCall(MethodNode node) {
                return node.getName().startsWith(term);
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitMethod(final MethodNode methodNode) {
        if (methodNode == null || !this.criteria.call(methodNode)) return;

        transformMethod(methodNode);
    }

    /**
     * Within this method developer could modify the {@link MethodNode}
     * instance.
     *
     * @param methodNode the {@link MethodNode}  you want to transform
     * @since 0.1.5
     */
    public abstract void transformMethod(MethodNode methodNode);

}
