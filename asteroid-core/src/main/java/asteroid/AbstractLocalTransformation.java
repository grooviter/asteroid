package asteroid;

import static org.codehaus.groovy.ast.ClassHelper.make;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.first;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.last;

import java.lang.annotation.Annotation;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;

/**
 * This class is an abstraction to process certain nodes annotated with a specific annotation node type
 * <br><br>
 * <b class="note">Types indicate wich nodes are affected:</b>
 * <br><br>
 * Lets say we wanted to build a transformation to transform methods annotated by {@literal @}br
 * <MyAnnotation><br>
 * <pre class="inner"><code>
 * public class MyCustomTransformation extends AbstractLocalTransformation&lt;MyAnnotation, MethodNode&gt; {
 *     public void doVisit(AnnotationNode annotation, final MethodNode annotated){
 *         // implementation
 *     }
 * }
 * </code></pre>
 * In this example transformation will be applied only to those {@link ASTNode} instances of type
 * {@link org.codehaus.groovy.ast.MethodNode} annotated by {@literal @}MyAnnotation
 * <br><br>
 * <b class="note">Checks (since 0.1.5):</b>
 * <br><br>
 * If you would like to check something before applying the
 * transformation you can use a contract-like programming
 * structure. If you have worked with <a
 * href="https://github.com/spockframework">Spock</a> or <a
 * href="https://github.com/andresteingress/gcontracts">GContracts</a>
 * you are already used to it. The idea is to have two blocks within
 * {@link AbstractLocalTransformation#doVisit} method, one for assertions,
 * the other to call the transformation.
 *
 * <pre class="inner"><code>
 *     public abstract void doVisit(AnnotationNode annotation, final ClassNode annotated){
 *         check: 'class has correct name'
 *         annotated.name == 'MyBusinessService'
 *
 *         then: 'we will add a new method'
 *         // transformation code
 *     }
 * </code></pre>
 * Any expression within the <b>check</b> block will be treated as an
 * assertion statement. If any of the assertion fails the compilation
 * will fail.
 * @param <T> The annotation type used to mark the transformation
 * @param <S> The annotated node type. It has to be a subtype
 * of {@link AnnotatedNode}. As a rule of thumb think of any type that
 * can be annotated (a method, a type...)
 * @since 0.2.0
 *
 */
public abstract class AbstractLocalTransformation<T extends Annotation,S extends AnnotatedNode>
    extends AbstractASTTransformation {

    private final Class<T> annotation;

    /**
     * Default constructor
     *
     * @throws IllegalAccessException
     * @since 0.2.0
     */
    @SuppressWarnings("PMD.SignatureDeclaredThrowsException")
    public AbstractLocalTransformation() throws IllegalAccessException {
        throw new IllegalAccessException("This method should never be used. It will be re-created by a local AST transformation");
    }

    /**
     * Default constructor
     *
     * @param annotation The type of the annotatino used to trigger the transformation
     * @since 0.2.0
     */
    public AbstractLocalTransformation(final Class<T> annotation) {
        this.annotation = annotation;
    }

    /**
     * This method processes all annotated nodes annotated with a specific annotation node.
     *
     * @param annotation the annotation information
     * @param annotated the ast node annotated with the specific annotation
     * @since 0.2.0
     */
    public abstract void doVisit(AnnotationNode annotation, final S annotated);

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ASTNode[] nodes, final SourceUnit source) {
        super.init(nodes, source);

        final AnnotationNode marker = (AnnotationNode) first(nodes);

        final ClassNode type = marker.getClassNode();
        final ClassNode reference = make(annotation);
        final Boolean isAnnotationOk = A.UTIL.CLASS.isOrExtends(type, reference);

        if (!isAnnotationOk) {
            return;
        }

        final S annotated = (S) last(nodes);

        doVisit(marker, annotated);
    }
}
