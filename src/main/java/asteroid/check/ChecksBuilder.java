package asteroid.check;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.SyntaxException;

import java.util.List;
import java.util.logging.Logger;

/**
 * Makes easier to concatenate a set of checkers. Normally it would be access through the {@link asteroid.A#CHECK} call.
 *
 * <pre>
 * <code>
 *     A.CHECK.source(sourceUnit)
 *            .checkThat(methodNode, hasName("expectedMethodName")
 *            .checkThat(annotation, isOnlyAppliedOnceTo(listOfMethodNodes))
 * </code>
 * </pre>
 *
 * Or if you are using Groovy code:
 *
 * <pre>
 * <code>
 *     A.CHECK.source(sourceUnit) {
 *         checkThat methodNode, hasName("expectedMethodName")
 *         checkThat annotation, isOnlyAppliedOnceTo(listOfMethods)
 *     }
 * </code>
 * </pre>
 *
 * @see Checker
 * @since 0.1.0
 */
public final class ChecksBuilder {

    private final SourceUnit sourceUnit;
    private static final Logger LOG = Logger.getLogger(ChecksBuilder.class.getName());

    /**
     * Creates an instance of ChecksBuilder
     *
     * @param sourceUnit an instance of {@link SourceUnit} is needed when throwing {@link SyntaxException} instances
     */
    private ChecksBuilder(final SourceUnit sourceUnit) {
        this.sourceUnit = sourceUnit;
    }

    /**
     * Creates an instance of {@link ChecksBuilder} for a given {@link SourceUnit}
     *
     * @param sourceUnit a {@link SourceUnit} is needed especially when throwing a {@link SyntaxException}
     * @return the current instance
     */
    public static ChecksBuilder source(final SourceUnit sourceUnit) {
        return new ChecksBuilder(sourceUnit);
    }

    /**
     * Creates an instance of {@link ChecksBuilder} for a given {@link SourceUnit} and creates a scope where
     * methods from {@link ChecksBuilder} can be invoked.
     *
     * @param sourceUnit a {@link SourceUnit} is needed especially when throwing a {@link SyntaxException}
     * @param closure Within this scope all methods from {@link ChecksBuilder} can be invoked.
     */
    public static void source(final SourceUnit sourceUnit, @DelegatesTo(ChecksBuilder.class) Closure<?> closure) {
        closure.setDelegate(new ChecksBuilder(sourceUnit));
        closure.call();
    }

    /**
     * Process a given checker over a given node
     *
     * @param node an instance of {@link AnnotatedNode}
     * @param checker a checker able to act over instances of {@link AnnotatedNode}
     * @return the current instance
     */
    public <T extends AnnotatedNode> ChecksBuilder checkThat(final T node, final Checker<T> checker) {
        processResult(checker.accepts(node), sourceUnit);
        return this;
    }

    /**
     * Process a given checker over a given node
     *
     * @param node an instance of {@link AnnotationNode}
     * @param checker a checker able to act over instances of {@link AnnotationNode}
     * @return the current instance
     */
    public <T extends AnnotationNode> ChecksBuilder checkThat(final T node, Checker<T> checker) {
        processResult(checker.accepts(node), sourceUnit);
        return this;
    }

    /**
     * Evaluates a given expression over a given node. If the expression evaluates to false then
     * a syntax error will be thrown
     *
     * @param node an instance of {@link AnnotationNode}
     * @param expression to evaluate{@link AnnotationNode}
     * @return the current instance
     */
    public <T extends AnnotationNode> ChecksBuilder checkTrue(final T node, Boolean expression, String message) {
        processResult(new Result(node, expression ? Result.Status.PASSED : Result.Status.ERROR, message), sourceUnit);
        return this;
    }

    /**
     * Evaluates a given expression over a given node. If the expression evaluates to true then
     * a syntax error will be thrown
     *
     * @param node an instance of {@link AnnotationNode}
     * @param expression to evaluate{@link AnnotationNode}
     * @return the current instance
     */
    public <T extends AnnotationNode> ChecksBuilder checkFalse(final T node, Boolean expression, String message) {
        processResult(new Result(node, !expression ? Result.Status.PASSED : Result.Status.ERROR, message), sourceUnit);
        return this;
    }

    private void processResult(final Result result, final SourceUnit sourceUnit) {
        switch(result.status) {
            case PASSED:
                break;
            case WARNING:
                LOG.warning(result.message);
                break;
            case ERROR:
                sourceUnit.addError(
                        new SyntaxException(
                                result.message,
                                result.nodeUnderTest.getLineNumber(),
                                result.nodeUnderTest.getColumnNumber()));
                break;
        }
    }

    /**
     * Creates an instance of {@link HasName}
     *
     * @param name The name of the expected {@link MethodNode}
     * @return an instance of checker of type {@link HasName}
     */
    public static Checker<MethodNode> hasName(String name) {
        return HasName.hasName(name);
    }

    /**
     * Creates an instance of {@link AppliedOnceTo}
     *
     * @param nodes The nodes the annotation will be checked against
     * @return an instance of {@link AppliedOnceTo} checker
     */
    public static <T extends AnnotatedNode> Checker<AnnotationNode> isAppliedOnceTo(final List<T> nodes) {
        return AppliedOnceTo.appliedOnceTo(nodes);
    }

}
