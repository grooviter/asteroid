package asteroid;

/**
 * Entry point to look up functions and utilities to work with nodes, expressions, statements,
 * node utils...etc
 *
 * @since 0.1.0
 */
@SuppressWarnings("PMD.ShortClassName")
public final class A {

    /**
     * Entry point to get node builders.
     *
     * @since 0.1.0
     */
    public static final Nodes NODES = new Nodes();

    /**
     * Entry point to access language modifiers
     *
     * @since 0.1.0
     */
    public static final Modifiers ACC = new Modifiers();

    /**
     * Entry point to create statements.
     *
     * @since 0.1.0
     */
    public static final Statements STMT = new Statements();

    /**
     * Entry point to create expressions
     *
     * @since 0.1.0
     */
    public static final Expressions EXPR = new Expressions();

    /**
     * Entry point to get util functions
     *
     * @since 0.1.0
     */
    public static final Utils UTIL = new Utils();
}
