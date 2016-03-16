package asteroid;

/**
 * Entry point to look up functions and utilities to work with nodes, expressions, statements,
 * node utils...etc
 *
 * @since 0.1.0
 */
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
     * Entry point to get statement builders.
     *
     * @since 0.1.0
     */
    public static final Statements STMT = new Statements();

    /**
     * Entry point to get expression builders
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

    /**
     * Entry point to build {@link org.codehaus.groovy.ast.ASTNode} checkers
     *
     * @since 0.1.0
     */
    public static final Checks CHECK = new Checks();

    /**
     * Compilation phases available for local transformations. Local transformations are only allowed to
     * access compilation phases from <strong>SEMANTIC_ANALYSIS</strong> to <strong>FINALIZATION</strong>.
     * Ealier compilation phases are only allowed to be access by global transformations.
     * The compilation order is:
     * <br><br>
     * <ul>
     *     <li>SEMANTIC_ANALYSIS</li>
     *     <li>CANONICALIZATION</li>
     *     <li>INSTRUCTION_SELECTION</li>
     *     <li>CLASS_GENERATION</li>
     *     <li>OUTPUT</li>
     *     <li>FINALIZATION</li>
     * </ul>
     *
     * @since 0.1.0
     */
    public static enum PHASE_LOCAL {
        /**
         * Consistency and validity checks
         *
         * @since 0.1.0
         */
        SEMANTIC_ANALYSIS,
        /**
         * Complete building the AST
         *
         * @since 0.1.0
         */
        CANONICALIZATION,
        /**
         * Instruction set is chosen, for example Java 6 or Java 7 bytecode level
         *
         * @since 0.1.0
         */
        INSTRUCTION_SELECTION,
        /**
         * Creates the bytecode of the class in memory
         *
         * @since 0.1.0
         */
        CLASS_GENERATION,
        /**
         * Write the binary output to the file system (*.class file)
         *
         * @since 0.1.0
         */
        OUTPUT,
        /**
         * Perform any last cleanup and closes the file
         *
         * @since 0.1.0
         */
        FINALIZATION
    }


    /**
     * Targets available to apply a specific transformation
     *
     * @since 0.1.0
     */
    public enum TO {
        /**
         * Applies to a given class field
         *
         * @since 0.1.0
         */
        FIELD,
        /**
         * Applies to a given class
         *
         * @since 0.1.0
         */
        TYPE,
        /**
         * Applies to a given method
         *
         * @since 0.1.0
         */
        METHOD
    }

}
