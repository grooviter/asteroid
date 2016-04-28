package asteroid.internal;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.first;

import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.transform.GroovyASTTransformation;

import java.util.List;
import java.util.Arrays;

import asteroid.A;
import asteroid.local.LocalTransformation;
import asteroid.local.AbstractLocalTransformation;

/**
 * This transformation makes easier to declare a given local transformation. It narrows the available
 * compilation phases to those only capable of being used in a local transformation. The way of declaring
 * the transformation makes clearer the fact that it is a local transformation.
 *
 * @since 0.1.0
 */
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
public class LocalTransformationTransformation extends AbstractLocalTransformation<LocalTransformation,ClassNode> {

    private static final String METHOD_DOVISIT = "doVisit";

    /**
     * Constructor using abstraction {@link AbstractLocalTransformation}
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
    public void doVisit(final AnnotationNode annotationNode, final ClassNode annotatedNode) {
        final CompilePhase phase = extractCompilePhaseFrom(annotationNode);

        Utils.addASTAnnotationsFromTo(annotatedNode, phase);
        addClassConstructor(annotatedNode);

        // tag::addCheckTo[]
        A.UTIL.CHECK.addCheckTo(A.UTIL.CLASS.findMethodByName(annotatedNode, METHOD_DOVISIT));
        // end::addCheckTo[]
        A.UTIL.CLASS.removeAnnotation(annotatedNode, annotationNode);
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

    private CompilePhase extractCompilePhaseFrom(final AnnotationNode annotationNode) {
        final String phaseAsString = A.UTIL.ANNOTATION.get(annotationNode, String.class);
        final CompilePhase compilePhase = CompilePhase.valueOf(phaseAsString);

        return compilePhase;
    }
}
