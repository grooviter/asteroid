package asteroid;

/**
 * This class acts as a proxy to access modifier codes found at {@link groovyjarjarasm.asm.Opcodes}.
 * Normally you should access them through {@link A} with the <strong>ACC</strong> key. E.g:
 * <pre>
 *     <code>
 * myNode.setModifiers(A.ACC.ACC_PUBLIC);
 *     </code>
 * </pre>
 *
 * @since 0.1.0
 */
public class Modifiers implements groovyjarjarasm.asm.Opcodes { }
