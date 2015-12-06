package asteroid.check;

import org.codehaus.groovy.ast.ASTNode;

/**
 * When a given {@link Checker} is processed through its {@link Checker#accepts(ASTNode)} method it will return a
 * result containing whether the cheker failed or it passed, and the correspondent message to inform what was the
 * problem.
 *
 * @since 0.1.0
 */
public final class Result {

    final String message;
    final ASTNode nodeUnderTest;
    final Status status;

    /**
     * Creates an instance of {@link Result}
     *
     * @param nodeUnderTest The node that will be used to tell the user what the error is related to
     * @param status The status represents whether the process has failed or not. It also can represent a warning.
     * @param message The message used in case of error or warning
     */
    public Result(final ASTNode nodeUnderTest, final Status status, String message) {
        this.nodeUnderTest = nodeUnderTest;
        this.status = status;
        this.message = message;
    }

    /**
     * A checker {@link Result}
     *
     * @since 0.1.0
     */
    public static enum Status {
        PASSED, WARNING, ERROR
    }
}
