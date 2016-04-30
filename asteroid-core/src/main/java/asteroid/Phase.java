package asteroid;

import org.codehaus.groovy.transform.GroovyASTTransformationClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.codehaus.groovy.control.CompilePhase;

/**
 * Compilation is required when declaring any {@link
 * AbstractLocalTransformation} or {@link
 * AbstractGlobalTransformation}.
 * @since 0.2.0
 */
@SuppressWarnings("PMD.LongVariable")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@GroovyASTTransformationClass("asteroid.internal.LocalTransformationTransformation")
public @interface Phase {
    /**
     * Sets the phase compilation
     *
     * @return the specific {@link CompilePhase} when transformation will be applied
     * @since 0.2.0
     */
    CompilePhase value();

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
     * @since 0.2.0
     */
    public static class LOCAL {
        /**
         * Consistency and validity checks
         *
         * @since 0.2.0
         */
        public static final CompilePhase SEMANTIC_ANALYSIS = CompilePhase.SEMANTIC_ANALYSIS;
        /**
         * Complete building the AST
         *
         * @since 0.2.0
         */
        public static final CompilePhase CANONICALIZATION = CompilePhase.CANONICALIZATION;
        /**
         * Instruction set is chosen, for example Java 6 or Java 7 bytecode level
         *
         * @since 0.2.0
         */
        public static final CompilePhase INSTRUCTION_SELECTION = CompilePhase.INSTRUCTION_SELECTION;
        /**
         * Creates the bytecode of the class in memory
         *
         * @since 0.2.0
         */
        public static final CompilePhase CLASS_GENERATION = CompilePhase.CLASS_GENERATION;
        /**
         * Write the binary output to the file system (*.class file)
         *
         * @since 0.2.0
         */
        public static final CompilePhase OUTPUT = CompilePhase.OUTPUT;
        /**
         * Perform any last cleanup and closes the file
         *
         * @since 0.2.0
         */
        public static final CompilePhase FINALIZATION = CompilePhase.FINALIZATION;
    }

    /**
     * Compilation phases available for global transformations:
     *
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
     * @since 0.1.2
     */
    public static class GLOBAL {
        /**
         * Source files are opened and environment configured
         *
         * @since 0.1.2
         */
        public static final CompilePhase INITIALIZATION = CompilePhase.INITIALIZATION;
        /**
         * The grammar is used to to produce tree of tokens
         * representing the source code
         *
         * @since 0.2.0
         */
        public static final CompilePhase PARSING = CompilePhase.PARSING;
        /**
         * An abstract syntax tree (AST) is created from token trees
         *
         * @since 0.2.0
         */
        public static final CompilePhase CONVERSION = CompilePhase.CONVERSION;
        /**
         * Consistency and validity checks
         *
         * @since 0.2.0
         */
        public static final CompilePhase SEMANTIC_ANALYSIS = CompilePhase.SEMANTIC_ANALYSIS;
        /**
         * Complete building the AST
         *
         * @since 0.2.0
         */
        public static final CompilePhase CANONICALIZATION = CompilePhase.CANONICALIZATION;
        /**
         * Instruction set is chosen, for example Java 6 or Java 7 bytecode level
         *
         * @since 0.2.0
         */
        public static final CompilePhase INSTRUCTION_SELECTION = CompilePhase.INSTRUCTION_SELECTION;
        /**
         * Creates the bytecode of the class in memory
         *
         * @since 0.2.0
         */
        public static final CompilePhase CLASS_GENERATION = CompilePhase.CLASS_GENERATION;
        /**
         * Write the binary output to the file system (*.class file)
         *
         * @since 0.2.0
         */
        public static final CompilePhase OUTPUT = CompilePhase.OUTPUT;
        /**
         * Perform any last cleanup and closes the file
         *
         * @since 0.2.0
         */
        public static final CompilePhase FINALIZATION = CompilePhase.FINALIZATION;
    }
}
