package asteroid.utils

import asteroid.A
import asteroid.Phase
import asteroid.utils.StatementUtils.Group
import asteroid.AbstractLocalTransformation

import groovy.transform.CompileStatic

import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.ast.expr.MapEntryExpression
import org.codehaus.groovy.ast.stmt.BlockStatement

@CompileStatic
@Phase(Phase.LOCAL.SEMANTIC_ANALYSIS)
class CollectLabelsImpl extends AbstractLocalTransformation<CollectLabels, MethodNode> {

    @Override
    void doVisit(AnnotationNode annotation, MethodNode node) {
        BlockStatement blockS = A.UTIL.NODE.getCodeBlock(node)
        List<Group> stmtGroups = A.UTIL.STMT.groupStatementsByLabel(blockS)
        List<MapEntryExpression> entries = stmtGroups.collect { Group group ->
            A.EXPR.mapEntryX(
                A.EXPR.constX(group.label.name),
                A.EXPR.constX(group.label.desc))
        }

        node.code = A.STMT.returnS(A.EXPR.mapX(entries))
    }
}
