package asteroid.internal;

import asteroid.A;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import groovy.transform.InheritConstructors;

/**
 * A set of utilities to implement base transformation classes
 *
 * @since 0.1.7
 */
public final class Utils {

    private Utils() { }

    /**
     * All transformation will be always annotated with:
     *
     * <ul>
     *    <li>{@link InheritConstructors}</li>
     *    <li>{@link GroovyASTTransformation}</li>
     * </ul>
     *
     * To avoid repeating this over and over again this method adds
     * both to the annotated {@link ClassNode} passed as first
     * argument
     *
     * @param annotated The {@link ClassNode} we want to add both
     * annotations to
     * @param compilePhase The {@link CompilePhase} used as argument for
     * the {@link GroovyASTTransformation} annotation.
     * @since 0.1.7
     */
    public static void addASTAnnotationsFromTo(final ClassNode annotated, final CompilePhase compilePhase) {
        final AnnotationNode groovyAnn = getGroovyAnnotation(compilePhase);
        final AnnotationNode inheritConsAnn = getInheritConstructorsAnnotation();

        annotated.addAnnotation(inheritConsAnn);
        annotated.addAnnotation(groovyAnn);
    }

    /**
     * Creates an {@link AnnotationNode} of type {@link GroovyASTTransformation}.
     *
     * @param compilePhase the target {@link CompilePhase} used as value of the
     * {@link GroovyASTTransformation} annotation
     * @return an instance of {@link GroovyASTTransformation} annotation
     * @since 0.1.7
     */
    public static AnnotationNode getGroovyAnnotation(final CompilePhase compilePhase) {
        final PropertyExpression valueExpr = getCompilePhaseAsPropertyExpression(compilePhase);

        return A.NODES.annotation(GroovyASTTransformation.class)
                .member("phase", valueExpr)
                .build();
    }

    /**
     * Creates a {@link PropertyExpression} containing a {@link CompilePhase}. This property
     * can be used, for instance, as default value for a given annotation.
     *
     * @param compilePhase an instance of {@link CompilePhase}
     * @return a {@link PropertyExpression}
     * @since 0.1.7
     */
    public static PropertyExpression getCompilePhaseAsPropertyExpression(final CompilePhase compilePhase) {
        return A.EXPR.propX(A.EXPR.classX(CompilePhase.class), A.EXPR.constX(compilePhase));
    }

    /**
     * Builds a {@link AnnotationNode} of type {@link InheritConstructors}
     *
     * @return a {@link AnnotationNode} representing a {@link InheritConstructors}
     * @since 0.1.7
     */
    public static AnnotationNode getInheritConstructorsAnnotation() {
        return A.NODES.annotation(InheritConstructors.class).build();
    }
}
