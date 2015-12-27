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
     */
    public static final Nodes NODES = new Nodes();

    /**
     * Entry point to access language modifiers
     */
    public static final Modifiers ACC = new Modifiers();

    /**
     * Entry point to get statement builders.
     */
    public static final Statements STMT = new Statements();

    /**
     * Entry point to get expression builders
     */
    public static final Expressions EXPR = new Expressions();

    /**
     * Entry point to get util functions
     */
    public static final Utils UTIL = new Utils();

    /**
     * Entry point to build {@link org.codehaus.groovy.ast.ASTNode} checkers
     */
    public static final Checks CHECK = new Checks();

    /**
     * Compilation phases available for local transformations. Local transformations are only allowed to
     * access compilation phases from <strong>CANONICALIZATION</strong> to <strong>FINALIZATION</strong>. Ealier
     * compilation phases are only allowed to be access by global transformations. The compilation order is:
     * <br><br>
     * <ul>
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
         */
        SEMANTIC_ANALYSIS,
        /**
         * Complete building the AST
         */
        CANONICALIZATION,
        /**
         * Instruction set is chosen, for example Java 6 or Java 7 bytecode level
         */
        INSTRUCTION_SELECTION,
        /**
         * Creates the bytecode of the class in memory
         */
        CLASS_GENERATION,
        /**
         * Write the binary output to the file system (*.class file)
         */
        OUTPUT,
        /**
         * Perform any last cleanup and closes the file
         */
        FINALIZATION
    }


    /**
     * Targets available to apply a specific transformation
     *
     * @since 0.1.0
     */
    public enum TO {
        TYPE,
        METHOD
    }

}
