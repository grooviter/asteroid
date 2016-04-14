package asteroid.internal;

import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.ast.ClassNode;

import groovy.transform.InheritConstructors;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.transform.GroovyASTTransformation;

import asteroid.A;
import asteroid.A.PHASE_GLOBAL;
import asteroid.global.ClassNodeTransformer;
import asteroid.global.GlobalTransformation;

/**
 * This {@link ClassNodeTransformer} will be applied to all {@link
 * ClassNode} instances annotated with {@link GlobalTransformation}.
 * <br><br>
 * It will add certain low level annotations to trigger the process of
 * the annotated transformation. These annotations are the java
 * retention and target annotations, and the Groovy transform
 * annotation.
 *
 * @since 0.1.6
 */
public class TranslateToGlobalTransform extends ClassNodeTransformer {

    private static final String BLANK = "";
    private static final String GLOBAL_TX_NAME = "GlobalTransformation";
    private static final String GLOBAL_PHASE_PREFIX = "A.PHASE_GLOBAL.";
    private static final String GLOBAL_PHASE_WRONG = "GlobalAnnotation compilation phase is wrong!!";
    private static final String GLOBAL_PHASE_MISSING = "GlobalAnnotation compilation phase is missing!!";

    /**
     * Constructor receiving the {@link SourceUnit}
     *
     * @param sourceUnit
     * @since 0.1.6
     */
    public TranslateToGlobalTransform(final SourceUnit sourceUnit) {
        super(sourceUnit, byAnnotationName(GLOBAL_TX_NAME));
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public void transformClass(final ClassNode annotatedNode) {
        AnnotationNode annotationNode = A.UTIL.CLASS.getAnnotationFrom(annotatedNode, GLOBAL_TX_NAME);

        addAnnotationsFromTo(annotationNode, annotatedNode);
    }

    private void addAnnotationsFromTo(final AnnotationNode annotationNode, final ClassNode annotatedNode) {
        CompilePhase compilePhase = extractCompilePhaseFromSafely(annotationNode);

        annotatedNode.addAnnotation(getInheritConstructorsAnnotation());
        annotatedNode.addAnnotation(getGroovyAnnotation(compilePhase));
    }

    private CompilePhase extractCompilePhaseFromSafely(final AnnotationNode annotationNode) {
        try {
            return extractCompilePhaseFrom(annotationNode);
        } catch(IllegalArgumentException iaex){
            throw new GroovyBugError(GLOBAL_PHASE_WRONG, iaex);
        } catch(Exception ex) {
            throw new GroovyBugError(GLOBAL_PHASE_MISSING, ex);
        }
    }

    private CompilePhase extractCompilePhaseFrom(final AnnotationNode annotationNode) {
        String        value         = A.UTIL.ANNOTATION.getStringValue(annotationNode);
        String        phaseAsString = value.replace(GLOBAL_PHASE_PREFIX, BLANK);
        A.PHASE_GLOBAL phaseGlobal  = A.PHASE_GLOBAL.valueOf(phaseAsString);
        CompilePhase  compilePhase  = toCompilePhase(phaseGlobal);

        return compilePhase;
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

    private AnnotationNode getInheritConstructorsAnnotation() {
        return A.NODES.annotation(InheritConstructors.class).build();
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
