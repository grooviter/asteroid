package asteroid.internal;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.first;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.last;

import asteroid.A;
import asteroid.A.PHASE_LOCAL;
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
 * This transformation makes easier to declare a given local transformation. It narrows the available
 * compilation phases to those only capable of being used in a local transformation. The way of declaring
 * the transformation makes clearer the fact that it is a local transformation.
 *
 * @since 0.1.0
 */
@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
public class LocalTransformationTransformation extends AbstractASTTransformation {

    /**
     * {@inheritDoc}
     *
     * @since 0.1.0
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
        addClassConstructor(annotatedNode);
    }

    private void addClassConstructor(final ClassNode annotatedNode) {
        List<GenericsType> generics = Arrays.asList(annotatedNode.getSuperClass().getGenericsTypes());
        ClassNode annotationType    = first(generics).getType();
        ClassNode transformedType   = last(generics).getType();

        Statement callSuper = A.STMT
                .ctorSuperS(
                    A.EXPR.classX(annotationType),
                    A.EXPR.classX(transformedType));

        ConstructorNode constructorNode = A.NODES
                .constructor(A.ACC.ACC_PUBLIC)
                .code(callSuper)
                .build();

        annotatedNode.addConstructor(constructorNode);
    }

    private void addAnnotationsFromTo(final AnnotationNode annotationNode, final ClassNode annotatedNode) {
        String        phaseAsString = A.UTIL.ANNOTATION.get(annotationNode, String.class);
        A.PHASE_LOCAL phaseLocal    = A.PHASE_LOCAL.valueOf(phaseAsString);
        CompilePhase  compilePhase  = toCompilePhase(phaseLocal);

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
        PropertyExpression propertyExpression =
                A.EXPR.propX(
                    A.EXPR.classX(CompilePhase.class),
                    A.EXPR.constX(compilationPhase.toString()));

        return A.NODES.annotation(GroovyASTTransformation.class)
                .member("phase", propertyExpression)
                .build();
    }

}
