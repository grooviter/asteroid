package asteroid.internal;

import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.ast.ClassNode;

import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.AnnotationNode;

import asteroid.A;
import asteroid.Phase;
import asteroid.AbstractGlobalTransformation;
import asteroid.transformer.AbstractClassNodeTransformer;

/**
 * This {@link AbstractClassNodeTransformer} will be applied to all {@link
 * ClassNode} instances annotated with {@link Phase}.
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
    private static final String TX_NAME = "Phase";
    private static final String PHASE_PFIX = "Phase.GLOBAL.";
    private static final String PHASE_PFIX_SHORT = "GLOBAL.";
    private static final String PHASE_WRONG = "GlobalAnnotation compilation phase is wrong!!";
    private static final String PHASE_MISSING = "GlobalAnnotation compilation phase is missing!!";

    /**
     * Constructor receiving the {@link SourceUnit}
     *
     * @param sourceUnit
     * @since 0.1.6
     */
    public TranslateToGlobalTransform(final SourceUnit sourceUnit) {
        super(sourceUnit, A.CRITERIA.byAnnotationSimpleName(TX_NAME));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void transformClass(final ClassNode annotated) {
        final ClassNode superClass = annotated.getSuperClass();

        if (superClass == null) {
            return;
        }

        final ClassNode reference = A.NODES.clazz(AbstractGlobalTransformation.class).build();
        final Boolean isGlobal = A.UTIL.NODE.isOrExtendsUnsafe(annotated, reference);

        if (!isGlobal) {
            return;
        }

        final AnnotationNode annotation = A.UTIL.NODE.getAnnotationFrom(annotated, TX_NAME);
        final CompilePhase phase = extractCompilePhaseFromSafely(annotation);

        TransformationUtils.addASTAnnotationsFromTo(annotated, phase);
        A.UTIL.NODE.removeAnnotation(annotated, annotation);
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
        final String       value         = A.UTIL.NODE.getStringValue(annotationNode);
        final String       phaseAsString = value
            .replace(PHASE_PFIX, BLANK)
            .replace(PHASE_PFIX_SHORT, BLANK);
        final CompilePhase compilePhase  = CompilePhase.valueOf(phaseAsString);

        return compilePhase;
    }
}
