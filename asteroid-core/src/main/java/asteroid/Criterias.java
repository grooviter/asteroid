package asteroid;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.every;

import groovy.lang.Closure;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.syntax.Types;

import asteroid.transformer.AbstractMethodNodeTransformer;
import asteroid.transformer.AbstractClassNodeTransformer;

/**
 * This class contains out-of-the-box criterias, they are used to help
 * out finding specific nodes. They can be used in transformer
 * constructors or when filtering lists of nodes.
 * <br/>
 * <br/>
 * <strong>ACCESS</strong>
 * <br/>
 * <br/>
 * You can access directly criterias from {@link A#CRITERIA} or by
 * importing this class directly.
 * <br/>
 * <br/>
 * <strong>USE CASES</strong>
 * <br/>
 * <br/>
 * Because transformers traverse a given AST tree, they need to know
 * which nodes they're interested in and leave the rest, and criterias
 * are exactly built to do that.
 * <br/>
 * <br/>
 * <strong>Transformers</strong>
 * <pre><code>
 * class AddPropertyToInnerClass extends AbstractClassNodeTransformer {
 *     AddPropertyToInnerClass(final SourceUnit sourceUnit) {
 *         super(sourceUnit,
 *               A.CRITERIA.byClassNodeNameContains('AddTransformerSpecExample$Input'))
 *     }
 * }
 * </code></pre>
 *
 * In this case the constructor receives as second argument a criteria
 * to apply the transformation only over a specific inner class.
 *
 * Although criterias were targeted for transformers they could be also
 * used in any other transformation.
 * <br/>
 * <br/>
 * But the way criterias were designed, make them also suitable for
 * using them to filter a list of especific nodes, expressions or
 * statements in any other situation.
 * <br/>
 * <br/>
 * <strong>Filtering</strong>
 * <pre><code>
 * List<MethodNode> finders = classNode
 * .getMethods()
 * .findAll(and(byMethodNodeNameStartsWith('find'),
 *              byMethodNodeNameContains('By')))
 * </code></pre>
 *
 * This last example could be a normal case in a local transformation
 * where you are interested in checking the existence of a certain
 * node in order to take a decision during the transformation.
 *
 * @since 0.2.4
 */
public final class Criterias {

    /*
     * ##################################
     * #### ANNOTATED NODE CRITERIAS ####
     * ##################################
     */

    /**
     * Criteria to find those annotated nodes with an annotation with
     * a {@link Class}.
     * <br/>
     * <br/>
     * <strong>ONLY</strong> use in a compilation phase where type information
     * is available (from SEMANTIC_ANALYSIS forwards)
     *
     * @param annotationClazz the type of the annotation
     * @return a criteria to look for annotated nodes annotated with a given type
     * @since 0.2.4
     */
    public static <A extends AnnotatedNode> Closure<Boolean> byAnnotation(final Class annotationClazz) {
        return new Closure<Boolean>(null) {
            public Boolean doCall(final A node) {
                return any(node.getAnnotations(), new Closure(null) {
                        Boolean doCall(final AnnotationNode annotationNode) {
                            return annotationNode
                                .getClassNode()
                                .getTypeClass()
                                .equals(annotationClazz);
                        }
                    });
            }
        };
    }

    /**
     * Criteria to find those annotated nodes with an annotation with
     * a {@link Class} with a name as the passed argument. This name
     * should be the same as using {@link Class#getSimpleName}
     * <br><br> This method doesn't use a {@link Class} as argument
     * cause the package (type information) won't be available for
     * earlier {@link CompilePhase}
     *
     * @param annotationName the simple name of the {@link Class} of the annotation used as markero
     * @return a criteria to look for annotated nodes annotated with a given type
     * @since 0.2.4
     */
    public static <A extends AnnotatedNode> Closure<Boolean> byAnnotationSimpleName(final String annotationName) {
        return new Closure<Boolean>(null) {
            public Boolean doCall(final A node) {
                return any(node.getAnnotations(), new Closure(null) {
                        public boolean doCall(final AnnotationNode annotationNode) {
                            return annotationNode
                                .getClassNode()
                                .getNameWithoutPackage()
                                .equals(annotationName);
                        }
                    });
            }
        };
    }

    /*
     * ##############################
     * #### METHODNODE CRITERIAS ####
     * ##############################
     */

    /**
     * Criteria to find those methods with a specific name
     *
     * @param methodName the name returned by {@link MethodNode#getName}
     * @return a criteria that can be used in a {@link AbstractMethodNodeTransformer} constructor
     * @since 0.2.4
     */
    public static <A extends MethodNode> Closure<Boolean> byMethodNodeName(final String methodName) {
        return new Closure<Boolean>(null) {
            public Boolean doCall(final A node) {
                return node.getName().equals(methodName);
            }
        };
    }

    /**
     * Criteria to find those methods with a name containing the term
     * passed as parameter
     *
     * @param term the term contained in the {@link MethodNode} name
     * @return a criteria that can be used in a {@link AbstractMethodNodeTransformer} constructor
     * @since 0.2.4
     */
    public static <A extends MethodNode> Closure<Boolean> byMethodNodeNameContains(final String term) {
        return new Closure<Boolean>(null) {
            public Boolean doCall(final A node) {
                return node.getName().contains(term);
            }
        };
    }

    /**
     * Criteria to find those methods with a name containing the term
     * passed as parameter at the end.
     *
     * @param suffix the term at the end of the {@link MethodNode} name
     * @return a criteria that can be used in a {@link AbstractMethodNodeTransformer} constructor
     * @since 0.2.4
     */
    public static <A extends MethodNode> Closure<Boolean> byMethodNodeNameEndsWith(final String suffix) {
        return new Closure<Boolean>(null) {
            public Boolean doCall(final A node) {
                return node.getName().contains(suffix);
            }
        };
    }

    /**
     * Criteria to find those methods with a name containing the term
     * passed as parameter at the beginning.
     *
     * @param prefix at the beginning of the {@link MethodNode} name
     * @return a criteria that can be used in a {@link AbstractMethodNodeTransformer} constructor
     * @since 0.2.4
     */
    public static <A extends MethodNode> Closure<Boolean> byMethodNodeNameStartsWith(final String prefix) {
        return new Closure<Boolean>(null) {
            public Boolean doCall(final A node) {
                return node.getName().contains(prefix);
            }
        };
    }

    /*
     * #############################
     * #### CLASSNODE CRITERIAS ####
     * #############################
     */

    /**
     * Criteria to find those classes with a name containing the term
     * passed as parameter
     *
     * @param term the term contained in the {@link ClassNode} name
     * @return a criteria that can be used in a {@link AbstractClassNodeTransformer} constructor
     * @since 0.2.4
     */
    public static <A extends ClassNode> Closure<Boolean> byClassNodeNameContains(final String term) {
        return new Closure<Boolean>(null) {
            public Boolean doCall(final A node) {
                return node.getName().contains(term);
            }
        };
    }

    /**
     * Criteria to find those classes with a name containing the term
     * passed as parameter at the end.
     *
     * @param term the term at the end of the {@link ClassNode} name
     * @return a criteria that can be used in a {@link AbstractClassNodeTransformer} constructor
     * @since 0.2.4
     */
    public static <A extends ClassNode> Closure<Boolean> byClassNodeNameEndsWith(final String term) {
        return new Closure<Boolean>(null) {
            public Boolean doCall(final A node) {
                return node.getName().endsWith(term);
            }
        };
    }

    /**
     * Criteria to find those classes with a name containing the term
     * passed as parameter at the beginning.
     *
     * @param term at the beginning of the {@link ClassNode} name
     * @return a criteria that can be used in a {@link AbstractClassNodeTransformer} constructor
     * @since 0.2.4
     */
    public static <A extends ClassNode> Closure<Boolean> byClassNodeNameStartsWith(final String term) {
        return new Closure<Boolean>(null) {
            public Boolean doCall(final A node) {
                return node.getName().startsWith(term);
            }
        };
    }

    /*
     * #############################
     * #### EXPRESSION CRITERIAS ###
     * #############################
     */

    /**
     * This method returns a criteria to look for {@link MethodCallExpression}
     * with a name equals to the name passed as parameter
     *
     * @param name the method name
     * @return a search criteria
     * @since 0.2.4
     */
    public static <A extends Expression> Closure<Boolean> byExprMethodCallByName(final String name) {
        return new Closure<Boolean>(null) {
            public Boolean doCall(final A expression) {
                if (!(expression instanceof MethodCallExpression)) {
                    return false;
                }

                final MethodCallExpression expr = (MethodCallExpression) expression;

                return expr.getMethodAsString().equals(name);
            }
        };
    }

    /**
     * This criteria will make the transformer to process every {@link Expression}
     *
     * @return a criteria to process everything
     * @since 0.2.4
     */
    public static <A extends Expression> Closure<Boolean> byExprAny() {
        return new Closure<Boolean>(null) {
            public Boolean doCall(final A expression) {
                return true;
            }
        };
    }

    /**
     * Checks that a given {@link BinaryExpression} uses a specific
     * token type. The token type is an `int` value. You can use
     * {@link Types} where all token types are declared.
     *
     * @param tokenType Check {@link Types} for more info
     * @return a {@link Closure} used as criteria
     * @since 0.2.4
     * @see Types
     */
    public static <A extends Expression> Closure<Boolean> byExprBinaryUsingToken(final int tokenType) {
        return new Closure<Boolean>(null) {
            public Boolean doCall(final A expression) {
                if (!(expression instanceof BinaryExpression)) {
                    return false;
                }

                final BinaryExpression binaryExpression = (BinaryExpression) expression;

                return binaryExpression
                    .getOperation()
                    .getType() == tokenType;
            }
        };
    }

    /*
     * #############################
     * #### STATEMENTS CRITERIAS ###
     * #############################
     */

    /**
     * This method returns a criteria to look for {@link Statement}
     * with a specific type
     *
     * @param stmtClass the type {@link java.lang.Class}
     * @return a search criteria
     * @since 0.2.4
     */
    public static <A extends Statement> Closure<Boolean> byStmtByType(final Class<A> stmtClass) {
        return new Closure<Boolean>(null) {
            public Boolean doCall(final A statement) {
                return stmtClass != null && stmtClass.isInstance(statement);
            }
        };
    }

    /*
     * #############################
     * ###### CRITERIAS UTILS ######
     * #############################
     */

    /**
     * Combines two {@link Closure} expressions returning a boolean.
     * The result will be a function that returns true if the
     * parameter passed makes any of the former functions to return
     * true.
     *
     * <strong>AST</strong>
     * <pre><code>def even     = { x -> x % 2 == 0 }
     *def positive = { y -> y > 0 }
     *
     *def evenOrPositive = or(even, positive)</code></pre>
     *
     * @param fns functions to combine
     * @return a combined {@link Closure}
     * @since 0.2.4
     */
    public static Closure<Boolean> or(final Closure<Boolean>... fns) {
        return new Closure(null) {
            public boolean doCall(final Object o) {
                return any(fns,
                           new Closure(null) {
                               public boolean doCall(final Closure<Boolean> fn) {
                                   return fn.call(o);
                               }
                           });
            }
        };
    }

    /**
     * Combines two {@link Closure} expressions returning a boolean.
     * The result will be a function that returns true only if the
     * parameter passed makes all of the former functions to return
     * true.
     *
     * <strong>AST</strong>
     * <pre><code>def even     = { x -> x % 2 == 0 }
     *def positive = { y -> y > 0 }
     *
     *def evenAndPositive = and(even, positive)</code></pre>
     *
     * @param fns functions to combine
     * @return a combined {@link Closure}
     * @since 0.2.4
     */
    public static Closure<Boolean> and(final Closure<Boolean>... fns) {
        return new Closure(null) {
            public boolean doCall(final Object o) {
                return every(fns,
                             new Closure(null) {
                                 public boolean doCall(final Closure<Boolean> fn) {
                                     return fn.call(o);
                                 }
                             });
            }
        };
    }
}
