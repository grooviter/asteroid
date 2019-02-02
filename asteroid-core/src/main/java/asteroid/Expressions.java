package asteroid;

import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.Types;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.expr.NotExpression;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.expr.MapEntryExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.IfStatement;

import java.util.List;
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
    public BooleanExpression boolEqualsNullX(final Expression argExpr) {
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
    public BooleanExpression boolHasSameFieldX(final FieldNode fNode, final Expression other) {
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
    public BooleanExpression boolHasSamePropertyX(final PropertyNode pNode, final Expression other) {
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
    public BooleanExpression boolIsInstanceOfX(final Expression objectExpression, final ClassNode cNode) {
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
    public BooleanExpression boolIsInstanceOfX(final Expression objectExpression, final Class cNode) {
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
    public BooleanExpression boolIsOneX(final Expression expr) {
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
    public BooleanExpression boolIsTrueX(final Expression argExpr) {
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
    public BooleanExpression boolIsZeroX(final Expression expr) {
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
    public BooleanExpression boolNotNullX(final Expression argExpr) {
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
    public BooleanExpression boolSameX(final Expression self, final Expression other) {
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
     * Builds an instance of {@link ConstantExpression} from the constant value
     * passed as argument, and it keeps the type as primitive if it has to.
     * <br><br>
     *
     * <strong>AST</strong>
     * <pre><code>
     * callThisX("println",
     *     constX(1, true) // constant value 1 of type primitive int
     * )
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>println 1 // print constant primitive type 1</code></pre>
     *
     * @param constant the constant value
     * @return an instance of {@link ConstantExpression}
     * @since 0.4.3
     */
    public static ConstantExpression constX(final Object constant, final Boolean keepPrimitive) {
        return GeneralUtils.constX(constant, keepPrimitive);
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
    public static PropertyExpression propX(final Expression owner, final Expression property) {
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
     * @param expressions list items as expressions
     * @return an instance of {@link ListExpression}
     * @since 0.1.0
     */
    public static ListExpression listX(final Expression... expressions) {
        return new ListExpression(Arrays.asList(expressions));
    }

    /**
     * Creates an instance of {@link MapExpression}:
     * <br/><br/>
     *
     * <strong>AST</strong>
     * <pre><code>mapX(mapEntryX(constX('key'), constX('value')))</code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>[key: 'value']</code></pre>
     *
     * @param expressions different map entries as {@link MapEntryExpression}
     * @return an instance of {@link MapExpression}
     * @since 0.2.8
     */
    public static MapExpression mapX(final MapEntryExpression... expressions) {
        return new MapExpression(Arrays.asList(expressions));
    }

    /**
     * Creates an instance of {@link MapExpression}:
     * <br/><br/>
     *
     * <strong>AST</strong>
     * <pre><code>mapX(mapEntryX(constX('key'), constX('value')))</code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>[key: 'value']</code></pre>
     *
     * @param expressions different map entries as {@link MapEntryExpression}
     * @return an instance of {@link MapExpression}
     * @since 0.2.8
     */
    public static MapExpression mapX(final List<MapEntryExpression> expressions) {
        return new MapExpression(expressions);
    }

    /**
     * Creates an instance of type {@link MapEntryExpression}
     *
     * @param key an {@link Expression} as the map entry key
     * @param value an {@link Expression} as the map entry value
     * @return an instance of {@link MapEntryExpression}
     * @since 0.2.8
     */
    public static MapEntryExpression mapEntryX(final Expression key, final Expression value) {
        return new MapEntryExpression(key, value);
    }

    /**
     * Creates an instance of type {@link MapEntryExpression} with the
     * key of type {@link String}
     *
     * @param key an {@link String} as the map entry key
     * @param value an {@link Expression} as the map entry value
     * @return an instance of {@link MapEntryExpression}
     * @since 0.4.3
     */
    public static MapEntryExpression mapEntryX(final String key, final Expression value) {
        return new MapEntryExpression(constX(key), value);
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
    public static ClassExpression classX(final Class clazz) {
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
    public static ClassExpression classX(final ClassNode classNode) {
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
    public static MethodCallExpression callThisX(final String methodName, final Expression... args) {
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
    public static MethodCallExpression callX(final Expression receiver, final String methodName, final Expression... args) {
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
    public static MethodCallExpression safeCallX(final Expression receiver, final String methodName, final Expression... args) {
        final MethodCallExpression mce = GeneralUtils.callX(receiver, methodName, new ArgumentListExpression(args));
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
    public static StaticMethodCallExpression staticCallX(final ClassNode clazz, final String methodName, final Expression... args) {
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
    public static StaticMethodCallExpression staticCallX(final Class clazz, final String methodName, final Expression... args) {
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
    public static FieldExpression fieldX(final FieldNode fieldNode) {
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
    public static MethodCallExpression callSuperX(final String methodName) {
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
    public static MethodCallExpression callSuperX(final String methodName, final Expression... args) {
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
    public static VariableExpression varX(final String varName) {
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
    public static VariableExpression varX(final String varName, final ClassNode type) {
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
    public static ClosureExpression closureX(final Statement stmt, final Parameter... params) {
        final ClosureExpression expr = GeneralUtils.closureX(params, stmt);
        expr.setVariableScope(new VariableScope());

        return expr;
    }

    /**
     *
     * When creating {@link BooleanExpression} in places such as
     * {@link IfStatement} you can use this method to create a {@link
     * BooleanExpression} out of a binary expression using the names
     * of the compared variables.
     *
     * <strong>AST</strong>
     * <pre><code>
     * //...after declaring the variables somewhere
     * boolX('johnAge', Types.COMPARE_GREATER_THAN, 'peterAge')
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * johnAge > peterAge
     * </code></pre>
     *
     * @param leftVarName name of the variable referenced in the left
     * side of the binary expression
     * @param tokenType type of the comparison operator. Use any of
     * the listed in {@link Types}
     * @param rightVarName name of the variable referenced in the
     * right side of the binary expression
     * @return a boolean expression as a {@link BooleanExpression} instance
     * @since 0.2.4
     */
    public static BooleanExpression boolX(final String leftVarName, final int tokenType, final String rightVarName) {
        return boolX(binX(leftVarName, tokenType, rightVarName));
    }

    /**
     *
     * When creating {@link BooleanExpression} in places such as
     * {@link IfStatement} you can use this method to create a {@link
     * BooleanExpression} out of a binary expression using constant
     * expressions
     *
     * <strong>AST</strong>
     * <pre><code>
     * //...after declaring the variables somewhere
     * boolX(constX(4), Types.COMPARE_GREATER_THAN, 2)
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * 4 > 2
     * </code></pre>
     *
     * @param leftExpr left expression
     * @param tokenType type of the comparison operator. Use any of
     * the listed in {@link Types}
     * @param rightExpr right expression
     * @return a boolean expression as a {@link BooleanExpression} instance
     * @since 0.2.4
     */
    public static BooleanExpression boolX(final Expression leftExpr, final int tokenType, final Expression rightExpr) {
        return boolX(binX(leftExpr, tokenType, rightExpr));
    }

    /**
     * Builds a new {@link BinaryExpression} with the names of the
     * variables pointing at both left/right terms in the binary
     * expression plus the operator in between. The operator can be
     * anyone found in the {@link Types} type
     *
     * <strong>AST</strong>
     * <pre><code>
     * //...after declaring the variables somewhere
     * boolX('johnAge', Types.PLUS, 'peterAge')
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * johnAge + peterAge
     * </code></pre>
     *
     * @param leftVarName name of the variable referenced in the left
     * side of the binary expression
     * @param operator type of the operator. Use any of the listed in
     * {@link Types}
     * @param rightVarName name of the variable referenced in the
     * right side of the binary expression
     * @return a binary expression as a {@link BinaryExpression} instance
     * @since 0.2.4
     */
    public static BinaryExpression binX(final String leftVarName, final int operator, final String rightVarName) {
        return new BinaryExpression(A.EXPR.varX(leftVarName),
                                    Token.newSymbol(operator, 0, 0),
                                    A.EXPR.varX(rightVarName));
    }

    /**
     * Builds a new {@link BinaryExpression} from a left expression
     * and a right expression joined by a given operator. The operator
     * can be anyone found in the {@link Types} type
     *
     * <strong>AST</strong>
     * <pre><code>
     * //...after declaring the variables somewhere
     * binX('johnAge', Types.PLUS, 'peterAge')
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * johnAge + peterAge
     * </code></pre>
     *
     * @param leftExpr left term in the binary expression
     * side of the binary expression
     * @param operator type of the comparison operator. Use any of
     * the listed in {@link Types}
     * @param rightExpr right term in the binary expression
     * @return a binary expression as a {@link BinaryExpression} instance
     * @since 0.2.4
     */
    public static BinaryExpression binX(final Expression leftExpr, final int operator, final Expression rightExpr) {
        return new BinaryExpression(leftExpr,
                                    Token.newSymbol(operator, 0, 0),
                                    rightExpr);
    }

    /**
     * Creates a variable definition expression. Where, in the code a
     * variable is defined. If could be also initialized.
     *
     * <strong>AST</strong>
     * <pre><code>
     * //...after declaring the variables somewhere
     * varDeclarationX('johnAge',
     *                  A.NODES.clazz(Integer).build(),
     *                  A.EXPR.constX(23))
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * Integer johnAge = 23
     * </code></pre>
     *
     * @param varName name of the declared variable
     * @param type type of the declared variable as a {@link ClassNode} instance
     * @param defaultValue expression setting the default value of the declared variable
     * @return an instance of a {@link DeclarationExpression}
     * @since 0.2.4
     */
    public static DeclarationExpression varDeclarationX(final String varName, final ClassNode type, final Expression defaultValue) {
        return new DeclarationExpression(
            A.EXPR.varX(varName, type),
            Token.newSymbol(Types.EQUAL, 0, 0),
            defaultValue);
    }

    /**
     * Creates a variable definition expression. Where, in the code a
     * variable is defined. If could be also initialized.
     *
     * <strong>AST</strong>
     * <pre><code>
     * //...after declaring the variables somewhere
     * varDeclarationX('johnAge', Integer, A.EXPR.constX(23))
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * Integer johnAge = 23
     * </code></pre>
     *
     * @param varName name of the declared variable
     * @param type type of the declared variable as a {@link Class} instance
     * @param defaultValue expression setting the default value of the declared variable
     * @return an instance of a {@link DeclarationExpression}
     * @since 0.2.4
     */
    public static DeclarationExpression varDeclarationX(final String varName, final Class type, final Expression defaultValue) {
        return new DeclarationExpression(
            A.EXPR.varX(varName, A.NODES.clazz(type).build()),
            Token.newSymbol(Types.EQUAL, 0, 0),
            defaultValue);
    }

    /**
     * <strong>AST</strong>
     * <pre><code>
     * newX(ArrayList)
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * new ArrayList()
     * </code></pre>
     *
     * @param type type of the target object
     * @return an instance of {@link ConstructorCallExpression}
     * @since 0.2.4
     */
    public static ConstructorCallExpression newX(final Class type) {
        return newX(type);
    }

    /**
     * <strong>AST</strong>
     * <pre><code>
     * newX(File, varX('path'), constX('myfile.txt'))
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * new File(path, 'myfile.txt')
     * </code></pre>
     *
     * @param type type of the target object
     * @param expressions a varargs of type {@link Expression}
     * @return an instance of {@link ConstructorCallExpression}
     * @since 0.2.4
     */
    public static ConstructorCallExpression newX(final Class type, final Expression... expressions) {
        return new ConstructorCallExpression(A.NODES.clazz(type).build(), new ArgumentListExpression(expressions));
    }

    /**
     * Negates a given expression
     *
     * <strong>AST</strong>
     * <pre><code>
     * notX(varX('myVariable'))
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * !myVariable
     * </code></pre>
     *
     * @param expression the expression to be negated
     * @return the expression passed as parameter negated
     * @since 0.2.4
     */
    public static NotExpression notX(final Expression expression) {
        return GeneralUtils.notX(expression);
    }

    /**
     * Builds a binary expression using a logical or (||)
     *
     * <strong>AST</strong>
     * <pre><code>
     * lorX(varX('a'), varX('b'))
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * a || b
     * </code></pre>
     *
     * @param leftExpr
     * @param rightExpr
     * @return a {@link BooleanExpression} representing a logical OR (||)
     * @since 0.2.4
     */
    public static BooleanExpression lorX(final Expression leftExpr, final Expression rightExpr) {
        return boolX(leftExpr, Types.LOGICAL_OR, rightExpr);
    }

    /**
     * Builds a binary expression using a logical and (&&)
     *
     * <strong>AST</strong>
     * <pre><code>
     * landX(varX('a'), varX('b'))
     * </code></pre>
     *
     * <strong>Result</strong>
     * <pre><code>
     * a && b
     * </code></pre>
     *
     * @param leftExpr
     * @param rightExpr
     * @return a {@link BooleanExpression} representing a logical AND (&&)
     * @since 0.2.4
     */
    public static BooleanExpression landX(final Expression leftExpr, final Expression rightExpr) {
        return boolX(leftExpr, Types.LOGICAL_AND, rightExpr);
    }
}
