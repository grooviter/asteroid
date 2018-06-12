package asteroid.global.samples

import asteroid.A
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.control.CompilationUnit
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer
import org.codehaus.groovy.transform.ASTTransformation
import spock.lang.Specification

class ChangeEqualsTransformationSpec extends Specification {

    void 'make sure CompilePhase works as a @Phase value'() {
        given: 'a script'
        String script = '''
          class Example {
            Boolean oneIsEqualsToOne() {
              return 1 == 1
            }
          }
        '''

        when:'generating the AST information'
        ClassNode classNode = extractClassNode('Example', script, new ChangeEqualsTransformation())

        and:'extracting the expression we are interested in'
        MethodNode oneIsEqualsToOne = A.UTIL.NODE.findMethodByName(classNode, 'oneIsEqualsToOne')
        BlockStatement blockStatement = A.UTIL.NODE.getCodeBlock(oneIsEqualsToOne)
        ReturnStatement returnStatement = blockStatement
            .statements
            .find({ Statement stmt ->
                stmt instanceof ReturnStatement
             })

        Expression expression = returnStatement.expression

        then: 'the binary expression should be now a method call expression'
        expression instanceof MethodCallExpression
    }

    ClassNode extractClassNode(String name, String script, ASTTransformation transformation) {
        CompilerConfiguration configuration = new CompilerConfiguration()
        ASTTransformationCustomizer customizer = new ASTTransformationCustomizer(transformation)

        configuration.addCompilationCustomizers(customizer)

        CompilationUnit compilationUnit = new CompilationUnit(configuration)
        compilationUnit.addSource(name, script)
        compilationUnit.compile()

        return compilationUnit.getClassNode(name)
    }
}
