package asteroid.internal;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.first;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.last;

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
import java.util.ArrayList;

import asteroid.A;
import asteroid.A.PHASE_GLOBAL;
import asteroid.global.GlobalTransformationImpl;
import asteroid.global.Transformer;

/**
 * This transformation makes easier to declare a given global transformation.
 *
 * @since 0.1.2
 */
@GroovyASTTransformation(phase = CompilePhase.CONVERSION)
public class GlobalTransformationTransformation extends GlobalTransformationImpl {

    @Override
    public List<Class<? extends Transformer>> getTransformers() {
        List<Class<? extends Transformer>> list = new ArrayList<>();
        list.add(TranslateToGlobalTransform.class);
        return list;
    }
}
