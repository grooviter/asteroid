package asteroid;

import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.VariableScope;

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
     * Builds an instance of {@link BooleanExpression} from a {@link Expression}
     * passed as argument.
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>
     * boolX(expr); // imagine expression is 1 == 1
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * 1 == 1 // as a boolean expression
     * </code></pre>
     *
     * @param expr the expression we want to evaluate as true or false
     * @return an instance of {@link BooleanExpression}
     * @see Expressions#binEqX
     * @see org.codehaus.groovy.syntax.Types#COMPARE_EQUAL
     * @since 0.1.5
     */
    public static BooleanExpression boolX(final Expression expr) {
        return new BooleanExpression(expr);
    }

    /**
     * Builds an instance of {@link BooleanExpression} of type:
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>
     * boolEqualsNullX(constX("some"));
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * "some" == null // as a boolean expression
     * </code></pre>
     *
     * @param argExpr the operand we want to check
     * @return an instance of {@link BooleanExpression}
     * @see org.codehaus.groovy.syntax.Types#COMPARE_EQUAL
     * @since 0.1.5
     */
    public BooleanExpression boolEqualsNullX(Expression argExpr) {
        return GeneralUtils.equalsNullX(argExpr);
    }

    /**
     * Builds an instance of {@link BooleanExpression} of type:
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>
     * boolHasSameFieldX(personNameFieldNode, anotherPersonRef);
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * name is (peter.name) // as a boolean expression
     * </code></pre>
     *
     * @param fNode the node we would like to check
     * @param other Another expression having as a result another
     * field we would like to check against
     * @return an instance of {@link BooleanExpression}
     * @since 0.1.5
     */
    public BooleanExpression boolHasSameFieldX(FieldNode fNode, Expression other) {
        return GeneralUtils.hasSameFieldX(fNode, other);
    }

    /**
     * Builds an instance of {@link BooleanExpression} of type:
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>
     * boolHasSamePropertyX(personNamePropertyNode, anotherPersonRef);
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * name is (peter.name) // as a boolean expression
     * </code></pre>
     *
     * @param pNode the node we would like to check
     * @param other Another expression having as a result another
     * property we would like to check against
     * @return an instance of {@link BooleanExpression}
     * @since 0.1.5
     */
    public BooleanExpression boolHasSamePropertyX(PropertyNode pNode, Expression other) {
        return GeneralUtils.hasSamePropertyX(pNode, other);
    }

    /**
     * Use it to create an "instanceof" expression to know whether an
     * instance is of a given type or not. Builds an instance of
     * {@link BooleanExpression} of type: <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>
     * boolIsInstanceOfX(stringExpression, stringClassNode);
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * "hello" instanceof String // as a boolean expression
     * </code></pre>
     *
     * @param objectExpression expression to evaluate
     * @param cNode a {@link ClassNode}
     * @return an instance of {@link BooleanExpression}
     * @since 0.1.5
     */
    public BooleanExpression boolIsInstanceOfX(Expression objectExpression, ClassNode cNode) {
        return GeneralUtils.isInstanceOfX(objectExpression, cNode);
    }

    /**
     * Use it to create an "instanceof" expression to know whether an
     * instance is of a given type or not. Builds an instance of
     * {@link BooleanExpression} of type:
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>boolIsInstanceOfX(stringExpression, stringClass)</code></pre>
     * <strong>Result</strong>
     * <pre><code>"hello" instanceof String // as a boolean expression</code></pre>
     *
     * <b class="warning">WARNING!:</b> Bear in mind that a given class might not be
     * available when using it in an AST transformation.
     *
     * @param objectExpression expression to evaluate
     * @param cNode a {@link java.lang.Class}. This
     * @return an instance of {@link BooleanExpression}
     * @since 0.1.5
     */
    public BooleanExpression boolIsInstanceOfX(Expression objectExpression, Class cNode) {
        return GeneralUtils.isInstanceOfX(objectExpression, A.NODES.clazz(cNode).build());
    }

    /**
     * Builds an instance of {@link BooleanExpression} of type:
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>
     * boolIsOneX(numberExpression);
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * 10 == 1 // as a boolean expression
     * </code></pre>
     *
     * Utility class to check a given expression equals to the constant
     * expression 1.
     *
     * @param expr expression to check against 1
     * @return an instance of {@link BooleanExpression}
     * @see org.codehaus.groovy.syntax.Types#COMPARE_EQUAL
     * @since 0.1.5
     */
    public BooleanExpression boolIsOneX(Expression expr) {
        return GeneralUtils.isOneX(expr);
    }

    /**
     * Builds an instance of {@link BooleanExpression} of type:
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>
     * boolIsTrueX(anyExpression);
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * "something" == Boolean.TRUE // as a boolean expression or...
     * (1 == 1) == Boolean.TRUE
     * </code></pre>
     *
     * Utility class to check a given expression equals to true
     *
     * @param argExpr expression checked to be true
     * @return an instance of {@link BooleanExpression}
     * @since 0.1.5
     */
    public BooleanExpression boolIsTrueX(Expression argExpr) {
        return GeneralUtils.isTrueX(argExpr);
    }

    /**
     * Builds an instance of {@link BooleanExpression} of type:
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>
     * boolIsZeroX(numberExpression);
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * 10 == 0 // as a boolean expression
     * </code></pre>
     *
     * Utility class to check a given expression equals to the constant
     * expression 0.
     *
     * @param expr expression to check against 0
     * @return an instance of {@link BooleanExpression}
     * @see org.codehaus.groovy.syntax.Types#COMPARE_EQUAL
     * @since 0.1.5
     */
    public BooleanExpression boolIsZeroX(Expression expr) {
        return GeneralUtils.isZeroX(expr);
    }

    /**
     * Builds an instance of {@link BooleanExpression} of type:
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>
     * boolEqualsNotNullX(constX("some"));
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * "some" != null // as a boolean expression
     * </code></pre>
     *
     * @param argExpr the expression   we want to check
     * @return an instance of {@link BooleanExpression}
     * @see org.codehaus.groovy.syntax.Types#COMPARE_EQUAL
     * @since 0.1.5
     */
    public BooleanExpression boolNotNullX(Expression argExpr) {
        return GeneralUtils.notNullX(argExpr);
    }

    /**
     * Builds an instance of {@link BooleanExpression} of type:
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>
     * boolSameX(self, another)
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * thisInstance is otherInstance
     * </code></pre>
     *
     * Use it to create expressions asking whether a given instance is
     * the same as another (like the == operator used in Java).
     *
     * @param self the expression we want to check against
     * @param other another expression
     * @return an instance of {@link BooleanExpression} answering
     * whether a given instance is the same as another.
     * @see org.codehaus.groovy.syntax.Types
     * @since 0.1.5
     */
    public BooleanExpression boolSameX(Expression self, Expression other) {
        return GeneralUtils.sameX(self, other);
    }

    /**
     * Builds an instance of {@link BinaryExpression} of type
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>
     * binEqX(constX(1), constX(1));
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>1 == 1</code></pre>
     *
     * @param leftExpr left hand side operand
     * @param rightExpr right hand side operand
     * @return an instance of {@link BinaryExpression} representing an
     * equals expression
     * @see org.codehaus.groovy.syntax.Types#COMPARE_EQUAL
     * @since 0.1.5
     */
    public static BinaryExpression binEqX(final Expression leftExpr, final Expression rightExpr) {
        return GeneralUtils.eqX(leftExpr, rightExpr);
    }

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
     * @since 0.1.0
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
     * @since 0.1.0
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
     * @since 0.1.0
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
     * @since 0.1.0
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
     * @since 0.1.0
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
      * @param args with different argument expressions
      * @return an instance of {@link MethodCallExpression}
      * @since 0.1.0
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
     * @param args with different argument expressions
     * @return an instance of {@link MethodCallExpression}
     * @since 0.1.0
     */
    public static MethodCallExpression callX(Expression receiver, String methodName, Expression... args) {
        return GeneralUtils.callX(receiver, methodName, new ArgumentListExpression(args));
    }

    /**
     * Creates a <b>safe</b> method call expression. It will return null if the receiver object
     * is null.
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>A.NODES.safeCallX(ownerExpression, "toMD5", A.EXPR.constX("hello"))</code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>owner?.toMD5("hello")</code></pre>
     *
     * @param receiver target instance expression
     * @param methodName The name of the method to invoke
     * @param args with different argument expressions
     * @return an instance of {@link MethodCallExpression}
     * @since 0.1.3
     */
    public static MethodCallExpression safeCallX(Expression receiver, String methodName, Expression... args) {
        MethodCallExpression mce = GeneralUtils.callX(receiver, methodName, new ArgumentListExpression(args));
        mce.setSafe(true);

        return mce;
    }

    /**
     * Creates a static method expression
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>A.NODES.staticCallX(systemClassNode, "currentTimeMillis")</code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>System.currentTimeMillis()</code></pre>
     *
     * @param clazz the {@link ClassNode} the method belongs
     * @param methodName The name of the method to invoke
     * @param args with different argument expressions
     * @return an instance of {@link StaticMethodCallExpression}
     * @since 0.1.0
     */
    public static StaticMethodCallExpression staticCallX(ClassNode clazz, String methodName, Expression... args) {
        return GeneralUtils.callX(clazz, methodName, new ArgumentListExpression(args));
    }

    /**
     * Creates a static method expression
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>A.NODES.staticCallX(systemClass, "currentTimeMillis")</code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>System.currentTimeMillis()</code></pre>
     *
     * @param clazz the {@link Class} the method belongs
     * @param methodName The name of the method to invoke
     * @param args with different argument expressions
     * @return an instance of {@link StaticMethodCallExpression}
     * @since 0.1.0
     */
    public static StaticMethodCallExpression staticCallX(Class clazz, String methodName, Expression... args) {
        return GeneralUtils.callX(ClassHelper.make(clazz, false), methodName, new ArgumentListExpression(args));
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
     * @return an instance of {@link FieldExpression}
     * @since 0.1.0
     */
    public static FieldExpression fieldX(FieldNode fieldNode) {
        return GeneralUtils.fieldX(fieldNode);
    }

    /**
     Creates a method call expression
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre>
     *     <code>
     *         class A {
     *             public void doSomething() {
     *                 super.doSomething()
     *             }
     *         }
     *
     *         //...
     *         MethodCallExpression = A.NODES.callSuperX("doSomething")
     *
     *     </code>
     * </pre>
     *
     * <strong>Result</strong>
     * <pre><code>super.doSomething()</code></pre>
     *
     * @param methodName name of the method we want to call
     * @return an instance of {@link MethodCallExpression}
     * @since 0.1.0
     */
    public static MethodCallExpression callSuperX(String methodName) {
        return GeneralUtils.callSuperX(methodName);
    }

    /**
     * Creates a method call expression
     *
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre>
     *     <code>
     *         class A {
     *             public void doSomething(String constant) {
     *                 super.doSomething("1")
     *             }
     *         }
     *
     *         //...
     *         MethodCallExpression = A.NODES.callSuperX("doSomething", constX("1"))
     *
     *     </code>
     * </pre>
     *
     * <strong>Result</strong>
     * <pre><code>super.doSomething("1")</code></pre>
     *
     * @param methodName name of the method we want to call
     * @param args expression representing different arguments to method call expression
     * @return an instance of {@link MethodCallExpression}
     * @since 0.1.0
     */
    public static MethodCallExpression callSuperX(String methodName, Expression... args) {
        return GeneralUtils.callSuperX(methodName, GeneralUtils.args(args));
    }

    /**
     * Creates a reference to a given variable
     *
     * Creates a method call expression
     *
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre>
     *     <code>
     *         class A {
     *             public void doSomething(String constant) {
     *                 super.doSomething(constant)
     *             }
     *         }
     *
     *         //...
     *         VariableExpression = A.NODES.varX("constant")
     *
     *     </code>
     * </pre>
     *
     * <strong>Result</strong>
     * <pre><code>constant</code></pre>
     *
     * @param varName
     * @return an instance of {@link VariableExpression}
     * @since 0.1.0
     */
    public static VariableExpression varX(String varName) {
        return GeneralUtils.varX(varName);
    }

    /**
     * Creates a reference to a given variable
     *
     * Creates a method call expression
     *
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre>
     *     <code>
     *         class A {
     *             public void doSomething(String constant) {
     *                 super.doSomething(constant)
     *             }
     *         }
     *
     *         //...
     *         VariableExpression = A.NODES.varX("constant", A.NODES.clazz(String))
     *
     *     </code>
     * </pre>
     *
     * <strong>Result</strong>
     * <pre><code>constant</code></pre>
     * Creates a reference to a given variable
     *
     * @param varName
     * @param type
     * @return an instance of {@link VariableExpression}
     * @since 0.1.0
     */
    public static VariableExpression varX(String varName, ClassNode type) {
        return GeneralUtils.varX(varName, type);
    }

    /**
     * Creates a closure expression. The statement passed as first parameter
     * becomes the closure's body.
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>
     * closureX(returnS(constantX("hello"))) // without params
     * closureX(statement, param("n",Integer)) // with a param
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * { -> "hello" } // without params
     * { Integer n -> n + 1 } // n + 1 is the statement
     * </code></pre>
     *
     * @param stmt the body of the closure
     * @param params the closure parameters
     * @return an instance of {@link ClosureExpression}
     * @since 0.1.5
     */
    public static ClosureExpression closureX(final Statement stmt, Parameter... params) {
        return GeneralUtils.closureX(params, stmt);
    }
}
