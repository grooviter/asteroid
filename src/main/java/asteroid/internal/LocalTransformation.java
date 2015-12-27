package asteroid.internal;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.first;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.last;
import static org.codehaus.groovy.ast.ClassHelper.make;

import asteroid.A;
import asteroid.Local;
import asteroid.Apply;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformationClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Reduces the boiler plate code when declaring an annotation as possible target for a given
 * transformation.
 *
 * It adds code such as the {@link java.lang.annotation.Retention} and {@link java.lang.annotation.Target} annotations as well as the
 * {@link org.codehaus.groovy.transform.GroovyASTTransformationClass}.
 *
 * This code:
 *
 * <pre>
 * <code>
 *   import my.transformation.MyTransformation;
 *
 *   {@literal @}Local(MyTransformation.class)
 *   public @interface MyAnnotation { }
 * </code>
 * </pre>
 *
 * Will become:
 *
 * <pre>
 * <code>
 *    {@literal @}Retention(RetentionPolicy.SOURCE)
 *    {@literal @}Target([ElementType.METHOD])
 *    {@literal @}GroovyASTTransformation("my.transformation.MyTransformation")
 *    public @interface MyAnnotation { }
 * </code>
 * </pre>
 *
 * @since 0.1.0
 *
 */
@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
public class LocalTransformation extends AbstractASTTransformation {

    @Override
    public void visit(final ASTNode[] nodes, final SourceUnit source) {
        this.sourceUnit = source;

        if (!check(nodes)) {
            return;
        }

        AnnotationNode annotationNode = A.UTIL.getFirstNodeAs(nodes, AnnotationNode.class);
        ClassNode      annotatedNode  = A.UTIL.getLastNodeAs(nodes, ClassNode.class);

        addAnnotationsFromTo(annotationNode, annotatedNode);
    }

    private void addAnnotationsFromTo(final AnnotationNode annotationNode, final ClassNode annotatedNode) {
        String clazzQualifiedName      = A.UTIL.get(annotationNode, String.class);
        AnnotationNode applyAnnotation = A.UTIL.getAnnotationFrom(annotatedNode, make(Apply.class));

        annotatedNode.addAnnotation(getTargetAnnotation(applyAnnotation));
        annotatedNode.addAnnotation(getRetentionAnnotation());
        annotatedNode.addAnnotation(getGroovyAnnotationWith(clazzQualifiedName));
    }

    private AnnotationNode getTargetAnnotation(AnnotationNode applyAnnotation) {
        String             targetString = applyAnnotation != null ?
            A.UTIL.get(applyAnnotation, String.class) :
            A.TO.TYPE.toString();

        ConstantExpression constantExpression = A.EXPR.constX(targetString);
        ClassExpression    classExpression    = A.EXPR.classX(ElementType.class);
        PropertyExpression propertyExpression = A.EXPR.propX(classExpression, constantExpression);
        ListExpression     listExpression     = A.EXPR.listX(propertyExpression);

        return A.NODES.annotation(Target.class)
                .member(A.UTIL.ANNOTATION_VALUE, listExpression)
                .build();
    }

    private AnnotationNode getRetentionAnnotation() {
        ClassExpression    classExpression    = A.EXPR.classX(RetentionPolicy.class);
        ConstantExpression constantExpression = A.EXPR.constX(RetentionPolicy.SOURCE.toString());
        PropertyExpression propertyExpression = A.EXPR.propX(classExpression, constantExpression);

        return A.NODES.annotation(Retention.class)
                .member(A.UTIL.ANNOTATION_VALUE, propertyExpression)
                .build();
    }

    private AnnotationNode getGroovyAnnotationWith(final String qualifiedName) {
        ConstantExpression constant = A.EXPR.constX(qualifiedName);

        return A.NODES.annotation(GroovyASTTransformationClass.class)
                .member(A.UTIL.ANNOTATION_VALUE, constant)
                .build();
    }

    private boolean check(final ASTNode[] nodes) {
        return !(thereIsNoNodes(nodes)                               ||
                thereAreOtherThanTwo(nodes)                          ||
                firstNodeIsNotAnAnnotation(nodes)                    ||
                firstNodeIsNotAnAnnotationOfType(nodes, Local.class) ||
                lastNodeIsNotAnAnnotatedNode(nodes));
    }

    private boolean thereIsNoNodes(final ASTNode[] nodes) {
        return nodes == null;
    }

    private boolean thereAreOtherThanTwo(final ASTNode[] nodes) {
        return nodes.length != 2;
    }

    private boolean firstNodeIsNotAnAnnotation(final ASTNode[] nodes) {
        return !(first(nodes) instanceof AnnotationNode);
    }

    private boolean lastNodeIsNotAnAnnotatedNode(final ASTNode[] nodes) {
        return !(last(nodes) instanceof AnnotatedNode);
    }

    private boolean firstNodeIsNotAnAnnotationOfType(final ASTNode[] nodes, final Class annotationType) {
        AnnotationNode annotationNode = A.UTIL.getFirstNodeAs(nodes, AnnotationNode.class);
        ClassNode annotationClassNode = A.NODES.clazz(annotationType).build();

        return !annotationNode.getClassNode().isDerivedFrom(annotationClassNode);
    }

}
