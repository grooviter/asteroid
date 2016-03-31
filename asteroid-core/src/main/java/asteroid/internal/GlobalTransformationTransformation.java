package asteroid.internal;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.first;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.last;

import asteroid.A;
import asteroid.A.PHASE_GLOBAL;
import groovy.transform.InheritConstructors;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

import java.util.Arrays;
import java.util.List;

/**
 * This transformation makes easier to declare a given global transformation.
 *
 * @since 0.1.2
 */
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
public class GlobalTransformationTransformation extends AbstractASTTransformation {

    /**
     * {@inheritDoc}
     *
     * @since 0.1.2
     */
    @Override
    public void visit(final ASTNode[] nodes, final SourceUnit source) {
        if (nodes == null) return;
        if (nodes.length != 2) return;
        if (!(nodes[0] instanceof AnnotationNode)) return;
        if (!(nodes[1] instanceof AnnotatedNode)) return;

        this.sourceUnit = source;

        AnnotationNode annotationNode = A.UTIL.MISC.getFirstNodeAs(nodes, AnnotationNode.class);
        ClassNode      annotatedNode  = A.UTIL.MISC.getLastNodeAs(nodes, ClassNode.class);

        addAnnotationsFromTo(annotationNode, annotatedNode);
    }

    private void addAnnotationsFromTo(final AnnotationNode annotationNode, final ClassNode annotatedNode) {
        String        phaseAsString = A.UTIL.ANNOTATION.get(annotationNode, String.class);
        A.PHASE_GLOBAL phaseGlobal    = A.PHASE_GLOBAL.valueOf(phaseAsString);
        CompilePhase  compilePhase  = toCompilePhase(phaseGlobal);

        annotatedNode.addAnnotation(A.NODES.annotation(InheritConstructors.class).build());
        annotatedNode.addAnnotation(getGroovyAnnotation(compilePhase));
    }

    private CompilePhase toCompilePhase(final PHASE_GLOBAL phase) {
        switch (phase) {
            case INITIALIZATION:
                return CompilePhase.INITIALIZATION;

            case PARSING:
                return CompilePhase.PARSING;

            case CONVERSION:
                return CompilePhase.CONVERSION;

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
        PropertyExpression propertyExpression =
                A.EXPR.propX(
                    A.EXPR.classX(CompilePhase.class),
                    A.EXPR.constX(compilationPhase.toString()));

        return A.NODES.annotation(GroovyASTTransformation.class)
                .member("phase", propertyExpression)
                .build();
    }

}
