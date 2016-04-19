package asteroid.internal;

import asteroid.A;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import groovy.transform.InheritConstructors;

public final class Utils {

    public static void addASTAnnotationsFromTo(final ClassNode to, CompilePhase compilePhase) {
        final AnnotationNode groovyAnn = getGroovyAnnotation(compilePhase);
        final AnnotationNode inheritConsAnn = getInheritConstructorsAnnotation();

        to.addAnnotation(inheritConsAnn);
        to.addAnnotation(groovyAnn);
    }

    public static AnnotationNode getGroovyAnnotation(final CompilePhase compilePhase) {
        final PropertyExpression valueExpr = getCompilePhaseAsPropertyExpression(compilePhase);

        return A.NODES.annotation(GroovyASTTransformation.class)
                .member("phase", valueExpr)
                .build();
    }

    public static PropertyExpression getCompilePhaseAsPropertyExpression(CompilePhase compilePhase) {
        return A.EXPR.propX(A.EXPR.classX(CompilePhase.class), A.EXPR.constX(compilePhase));
    }

    public static AnnotationNode getInheritConstructorsAnnotation() {
        return A.NODES.annotation(InheritConstructors.class).build();
    }
}
