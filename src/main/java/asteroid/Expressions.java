package asteroid;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.tools.GeneralUtils;

import java.util.Arrays;

/**
 *
 * This class hides the different implementations to create expressions through the Groovy api to provide a unified
 * an easier way to create expressions when coding an AST transformation.
 *
 * Normally users should access this class invoking {@link A#EXPR}:
 * <pre><code>
 * A.EXPR.propX(owner, property)
 * A.EXPR.listX(expression1, expression2)
 * // ...etc
 * </code></pre>
 *
 * @since 0.1.0
 */
public final class Expressions {

    /**
     * Builds an instance of {@link ConstantExpression} from the constant value
     * passed as argument
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>
     * callThisX("println",
     *     constX("1") // constant value "1"
     * )
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>println "1" // print constant value "1"</code></pre>
     *
     * @param constant the constant value
     * @return an instance of {@link ConstantExpression}
     */
    public static ConstantExpression constX(final Object constant) {
        return GeneralUtils.constX(constant);
    }

    /**
     * Builds an instance of {@link PropertyExpression}:
     * <br><br>
     *
     * <strong>AST</strong>:
     * <pre><code>propX("this", constX("name"))</code></pre>
     *
     * <strong>Result</strong>:
     * <pre><code>this.name</code></pre>
     *
     * @param owner the instance the property belongs to
     * @param property
     * @return an instance of {@link PropertyExpression}
     */
    public static PropertyExpression propX(Expression owner, Expression property) {
        return PropertyExpression.class.cast(GeneralUtils.propX(owner, property));
    }

    /**
     * Creates an instance of {@link ListExpression}:
     * <br><br>
     *
     * <strong>AST</strong>:
     * <pre><code>listX(constX(1), constX(2))</code></pre>
     *
     * <strong>Result</strong>:
     * <pre><code>[1,2]</code></pre>
     *
     * @param expressions
     * @return an instance of {@link ListExpression}
     */
    public static ListExpression listX(Expression... expressions) {
        return new ListExpression(Arrays.asList(expressions));
    }

    /**
     * Creates an instance of {@link ClassExpression} from a {@link Class}
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>classX(String.class)</code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>String.class</code></pre>
     *
     * @param clazz
     * @return an instance of {@link ClassExpression}
     */
    public static ClassExpression classX(Class clazz) {
        return GeneralUtils.classX(clazz);
    }

    /**
     * Creates a class expression from a {@link ClassNode}
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>classX(A.NODES.clazz(String.class))</code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>String.class</code></pre>
     *
     * @param classNode
     * @return an instance of {@link ClassNode}
     */
    public static ClassExpression classX(ClassNode classNode) {
        return GeneralUtils.classX(classNode);
    }

    /**
      * Creates a method call expression using `this` as target instance
      * <br><br>
      *
      * <strong>AST</strong>
      * <pre><code>A.NODES.callThisX("println", A.EXPR.constX("hello"))</code></pre>
      *
      * <strong>Result</strong>
      * <pre><code>println "hello"</code></pre>
      *
      * @param methodName The name of the method to invoke
      * @param varargs with different argument expressions
      * @return an instance of {@link MethodCallExpression}
      */
    public static MethodCallExpression callThisX(String methodName, Expression... args) {
        return GeneralUtils.callThisX(methodName, new ArgumentListExpression(args));
    }

    /**
     * Creates a method call expression
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>A.NODES.callX(ownerExpression, "toMD5", A.EXPR.constX("hello"))</code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>owner.toMD5("hello")</code></pre>
     *
     * @param receiver target instance expression
     * @param methodName The name of the method to invoke
     * @param varargs with different argument expressions
     * @return an instance of {@link MethodCallExpression}
     */
    public static MethodCallExpression callX(Expression receiver, String methodName, Expression... args) {
        return GeneralUtils.callX(receiver, methodName, new ArgumentListExpression(args));
    }

    /**
     * Creates a method call expression
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre>
     *     <code>
     *         class A {
     *             Integer age
     *         }
     *         //...
     *         FieldExpression node = A.NODES.fieldX(ageNode)
     *         A.EXPR.callX(node, "toString")
     *     </code>
     * </pre>
     *
     * <strong>Result</strong>
     * <pre><code>age.toString()</code></pre>
     *
     * @param fieldNode the node pointing at the field
     * @return an instance of {@link FielExpression}
     */
    public static FieldExpression fieldX(FieldNode fieldNode) {
        return GeneralUtils.fieldX(fieldNode);
    }

}
