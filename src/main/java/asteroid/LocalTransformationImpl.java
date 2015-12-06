package asteroid;

import static org.codehaus.groovy.ast.ClassHelper.make;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.first;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.last;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;

/**
 * This class is an abstraction to process certain nodes annotated with a specific annotation node type
 *
 * @param <ANNOTATION> The annotation type used to mark the transformation
 * @param <ANNOTATED> The annotated node type. It has to be a subtype of {@link AnnotatedNode}
 * <br><br>
 * Lets say we wanted to build a transformation to transform methods annotated by {@literal @}MyAnnotation
 *
 * <pre><code>
 * public class MyCustomTransformation extends LocalTransformationImpl&lt;MyAnnotation, MethodNode&gt; {
 *     public abstract void doVisit(AnnotationNode annotation, final MethodNode annotated, final SourceUnit source){
 *         // implementation
 *     }
 * }
 * </code></pre>
 * In this example transformation will be applied only to those {@link ASTNode} instances of type
 * {@link org.codehaus.groovy.ast.MethodNode} annotated by {@literal @}MyAnnotation
 *
 * @since 0.1.0
 *
 */
public abstract class LocalTransformationImpl<ANNOTATION,ANNOTATED extends AnnotatedNode>
    extends AbstractASTTransformation {

    public LocalTransformationImpl() throws Exception {
        throw new RuntimeException("This method should never be used. It will be re-created by a local AST transformation");
    }

    private final Class<ANNOTATION> annotation;
    private final Class<ANNOTATED> annotated;

    /**
     * Default constructor
     *
     * @param annotation The type of the annotatino used to trigger the transformation
     * @param annotated The type of node marked to be transformed
     */
    public LocalTransformationImpl(final Class<ANNOTATION> annotation, final Class<ANNOTATED> annotated) {
        this.annotation = annotation;
        this.annotated = annotated;
    }

    /**
     * This method processes all annotated nodes annotated with a specific annotation node.
     *
     * @param annotation the annotation information
     * @param annotated the ast node annotated with the specific annotation
     * @param source the current source unit available. It could be needed to for instance add a compilation error.
     */
    public abstract void doVisit(AnnotationNode annotation, final ANNOTATED annotated, final SourceUnit source);

    @Override
    public void visit(final ASTNode[] nodes, final SourceUnit source) {
        if (nodes == null) return;
        if (nodes.length != 2) return;
        if (!(first(nodes) instanceof AnnotationNode)) return;
        if (!(last(nodes) instanceof AnnotatedNode)) return;

        this.sourceUnit = source;

        AnnotationNode annotationNode = (AnnotationNode) first(nodes);
        ANNOTATED annotatedNode = (ANNOTATED) last(nodes);

        if (!annotationNode.getClassNode().isDerivedFrom(make(annotation))) return;

        doVisit(annotationNode, annotatedNode, source);
    }

}
