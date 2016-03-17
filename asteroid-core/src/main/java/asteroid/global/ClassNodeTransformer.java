package asteroid.global;

import org.codehaus.groovy.ast.expr.Expression;
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
 *    <li>{@link ClassNodeTransformer#byNameContains}</li>
 *    <li>{@link ClassNodeTransformer#byNameStartsWith}</li>
 *    <li>{@link ClassNodeTransformer#byNameEndsWith}</li>
 * </ul>
 *
 * @since 0.1.2
 */
public abstract class ClassNodeTransformer extends Transformer {

    private Closure<Boolean> criteria;

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
     * @since 0.1.2
     * @see ClassNodeTransformer#byNameContains
     */
    public ClassNodeTransformer(SourceUnit sourceUnit, Closure<Boolean> criteria) {
        super(sourceUnit);
        this.criteria = criteria;
    }

    /**
     * Criteria to find those classes with a name containing the term
     * passed as parameter
     *
     * @param term the term contained in the {@link ClassNode} name
     * @return a criteria to use in the {@link ClassNodeTransformer} constructor
     * @since 0.1.2
     */
    public static Closure<Boolean> byNameContains(final String term) {
        return new Closure<Boolean>(null) {
            public Boolean doCall(ClassNode node) {
                return node.getName().contains(term);
            }
        };
    }

    /**
     * Criteria to find those classes with a name containing the term
     * passed as parameter at the end.
     *
     * @param term the term at the end of the {@link ClassNode} name
     * @return a criteria to use in the {@link ClassNodeTransformer} constructor
     * @since 0.1.2
     */
    public static Closure<Boolean> byNameEndsWith(final String term) {
        return new Closure<Boolean>(null) {
            public Boolean doCall(ClassNode node) {
                return node.getName().endsWith(term);
            }
        };
    }

    /**
     * Criteria to find those classes with a name containing the term
     * passed as parameter at the beginning.
     *
     * @param term at the beginning of the {@link ClassNode} name
     * @return a criteria to use in the {@link ClassNodeTransformer} constructor
     * @since 0.1.2
     */
    public static Closure<Boolean> byNameStartsWith(final String term) {
        return new Closure<Boolean>(null) {
            public Boolean doCall(ClassNode node) {
                return node.getName().startsWith(term);
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitClass(final ClassNode classNode) {
        if (classNode == null || !this.criteria.call(classNode)) return;

        transformClass(classNode);
    }

    /**
     * This method will transform the expression into its final version.
     *
     * @param expression the class expression you want to transform
     * @return the final version of the class expression
     * @since 0.1.2
     */
    public abstract void transformClass(ClassNode classNode);

}
