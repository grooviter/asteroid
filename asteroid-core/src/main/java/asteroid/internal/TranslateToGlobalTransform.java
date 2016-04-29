package asteroid.internal;

import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.ast.ClassNode;

import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.AnnotationNode;

import asteroid.A;
import asteroid.GlobalTransformation;
import asteroid.transformer.AbstractClassNodeTransformer;

/**
 * This {@link AbstractClassNodeTransformer} will be applied to all {@link
 * ClassNode} instances annotated with {@link GlobalTransformation}.
 * <br><br>
 * It will add certain low level annotations to trigger the process of
 * the annotated transformation. These annotations are the java
 * retention and target annotations, and the Groovy transform
 * annotation.
 *
 * @since 0.1.6
 */
public class TranslateToGlobalTransform extends AbstractClassNodeTransformer {

    private static final String BLANK = "";
    private static final String TX_NAME = "GlobalTransformation";
    private static final String PHASE_PREFIX = "A.PHASE_GLOBAL.";
    private static final String PHASE_WRONG = "GlobalAnnotation compilation phase is wrong!!";
    private static final String PHASE_MISSING = "GlobalAnnotation compilation phase is missing!!";

    /**
     * Constructor receiving the {@link SourceUnit}
     *
     * @param sourceUnit
     * @since 0.1.6
     */
    public TranslateToGlobalTransform(final SourceUnit sourceUnit) {
        super(sourceUnit, byAnnotationName(TX_NAME));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void transformClass(final ClassNode annotated) {
        final AnnotationNode annotation = A.UTIL.CLASS.getAnnotationFrom(annotated, TX_NAME);
        final CompilePhase phase = extractCompilePhaseFromSafely(annotation);

        TransformationUtils.addASTAnnotationsFromTo(annotated, phase);
        A.UTIL.CLASS.removeAnnotation(annotated, annotation);
    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    private CompilePhase extractCompilePhaseFromSafely(final AnnotationNode annotationNode) {
        try {
            return extractCompilePhaseFrom(annotationNode);
        } catch(IllegalArgumentException iaex){
            throw new GroovyBugError(PHASE_WRONG, iaex);
        } catch(Exception ex) {
            throw new GroovyBugError(PHASE_MISSING, ex);
        }
    }

    private CompilePhase extractCompilePhaseFrom(final AnnotationNode annotationNode) {
        final String       value         = A.UTIL.ANNOTATION.getStringValue(annotationNode);
        final String       phaseAsString = value.replace(PHASE_PREFIX, BLANK);
        final CompilePhase compilePhase  = CompilePhase.valueOf(phaseAsString);

        return compilePhase;
    }
}
