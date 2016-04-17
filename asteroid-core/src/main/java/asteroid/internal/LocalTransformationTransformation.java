package asteroid.internal;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.first;

import groovy.transform.InheritConstructors;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.GroovyASTTransformation;

import java.util.List;
import java.util.Arrays;

import asteroid.A;
import asteroid.A.PHASE_LOCAL;
import asteroid.local.LocalTransformation;
import asteroid.local.LocalTransformationImpl;

/**
 * This transformation makes easier to declare a given local transformation. It narrows the available
 * compilation phases to those only capable of being used in a local transformation. The way of declaring
 * the transformation makes clearer the fact that it is a local transformation.
 *
 * @since 0.1.0
 */
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
public class LocalTransformationTransformation extends LocalTransformationImpl<LocalTransformation,ClassNode> {


    /**
     * Constructor using abstraction {@link LocalTransformationImpl}
     *
     * @since 0.1.6
     */
    public LocalTransformationTransformation() {
        super(LocalTransformation.class);
    }

    /**
     * {@inheritDoc}
     *
     * @since 0.1.6
     */
    @Override
    public void doVisit(final AnnotationNode annotationNode, final ClassNode annotatedNode, final SourceUnit source) {
        addAnnotationsFromTo(annotationNode, annotatedNode);
        addClassConstructor(annotatedNode);

        // tag::addCheckTo[]
        A.UTIL.CHECK.addCheckTo(A.UTIL.CLASS.findMethodByName(annotatedNode, "doVisit"));
        // end::addCheckTo[]
    }

    private void addClassConstructor(final ClassNode annotatedNode) {
        final List<GenericsType> generics = Arrays.asList(annotatedNode.getSuperClass().getGenericsTypes());
        final ClassNode annotationType    = first(generics).getType();

        final Statement callSuper = A.STMT
            .ctorSuperS(A.EXPR.classX(annotationType));

        final ConstructorNode constructorNode = A.NODES
            .constructor(A.ACC.ACC_PUBLIC)
            .code(callSuper)
            .build();

        annotatedNode.addConstructor(constructorNode);
    }

    private void addAnnotationsFromTo(final AnnotationNode annotationNode, final ClassNode annotatedNode) {
        final String        phaseAsString = A.UTIL.ANNOTATION.get(annotationNode, String.class);
        final A.PHASE_LOCAL phaseLocal    = A.PHASE_LOCAL.valueOf(phaseAsString);
        final CompilePhase  compilePhase  = toCompilePhase(phaseLocal);

        annotatedNode.addAnnotation(A.NODES.annotation(InheritConstructors.class).build());
        annotatedNode.addAnnotation(getGroovyAnnotation(compilePhase));
    }

    private CompilePhase toCompilePhase(final PHASE_LOCAL phase) {
        switch (phase) {
            case SEMANTIC_ANALYSIS:
                return CompilePhase.SEMANTIC_ANALYSIS;

            case CANONICALIZATION:
                return CompilePhase.CANONICALIZATION;

            case INSTRUCTION_SELECTION:
                return CompilePhase.INSTRUCTION_SELECTION;

            case CLASS_GENERATION:
                return CompilePhase.CLASS_GENERATION;

            case OUTPUT:
                return CompilePhase.OUTPUT;

            default:
                return CompilePhase.INSTRUCTION_SELECTION;
        }
    }

    private AnnotationNode getGroovyAnnotation(final CompilePhase compilationPhase) {
        final PropertyExpression propertyExpr =
                A.EXPR.propX(
                    A.EXPR.classX(CompilePhase.class),
                    A.EXPR.constX(compilationPhase.toString()));

        return A.NODES.annotation(GroovyASTTransformation.class)
                .member("phase", propertyExpr)
                .build();
    }

}
