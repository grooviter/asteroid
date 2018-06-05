package asteroid.utils;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.inject;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.last;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import asteroid.Expressions;
import groovy.lang.Closure;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.Statement;

/**
 * General utility methods to deal with {@link Statement} instances
 *
 * @since 0.1.5
 * @see asteroid.Utils
 */
public final class StatementUtils {

    /**
     * Represent a group of statements grouped by a given label.
     *
     * @since 0.1.5
     */
    @SuppressWarnings("PMD.BeanMembersShouldSerialize")
    public static class Group {
        public final Label label;
        public final List<Statement> statements;

        /**
         * Default constructor
         *
         * @param label the label identifying this group
         * @param statements the statements that belong to this group
         */
        Group(final Label label, final List<Statement> statements) {
            this.label      = label;
            this.statements = statements;
        }

        /**
         * Utility method to clone a given {@link Group} changing the statement list
         *
         * @param newStatements the new statements of the group
         * @return a new {@link Group}
         */
        public Group copyWithStatements(final List<Statement> newStatements) {
            return new Group(new Label(label.name, label.expression), newStatements);
        }
    }

    /**
     * This class helps to identify a list of statements.
     *
     * @since 0.1.5
     */
    @SuppressWarnings("PMD.BeanMembersShouldSerialize")
    public static class Label {
        public final String name;
        public final Expression expression;

        /**
         * Default constructor
         *
         * @param name the name found in the label
         * @param expression could be any expression. It's normally an expression
         */
        Label(final String name, final Expression expression) {
            this.name = name;
            this.expression = expression;
        }

        /**
         * Returns the label name as a {@link ConstantExpression}
         *
         * @return the label name as a {@link ConstantExpression}
         * @since 0.4.0
         */
        public ConstantExpression nameAsExpression() {
            return name != null ? Expressions.constX(name) : null;
        }
    }

    /**
     * Use this function to group expression statements found within a
     * given block statement. Statements will be group in {@link
     * Group} instances.
     *
     * @param blockStmt the block statement where the statements we
     * want to group are
     * @return a list of {@link Group}
     * @since 0.1.5
     */
    public List<Group> groupStatementsByLabel(final BlockStatement blockStmt) {
        return (List<Group>) inject(blockStmt.getStatements(), new ArrayList<Group>(), groupByLabel());
    }

    /*
     * Creates a list of groups of statements by labels.
     *
     * IMPORTANT: Statements not included in any label statement will
     * be discarded.
     */
    private Closure<List<Group>> groupByLabel() {
        return new Closure<List<Group>>(null) {
            public List<Group> doCall(final List<Group> acc, final Statement stmt) {
                final Label label        = extractLabelFrom(stmt);
                final Group currentGroup = acc.isEmpty() ? null : last(acc);

                /* If there is no label and there is a current group then add
                   the statement to the current group */
                if (label == null && currentGroup != null) {
                    currentGroup.statements.add(stmt);
                } else if (label != null){
                    /* otherwise and only if there is a new label, we create a new group */
                    acc.add(new Group(label, new ArrayList<Statement>()));
                }

                return acc;
            }
        };
    }

    /**
     * If you would like to take the text from a label in your code
     * <pre><code>
     *  check: 'is greater than'
     *  //...
     * </code></pre>
     *
     * @param stmt The statement we would like to take a label from
     * @return an instance of {@link Label}
     */
    public Label extractLabelFrom(final Statement stmt) {
        final boolean isExprStmt = stmt instanceof ExpressionStatement;

        /* If the expression is not an expression statement there is nothing to do */
        if (!isExprStmt) {
            return null;
        }

        final ExpressionStatement exprStmt = (ExpressionStatement) stmt;
        final String labelName = exprStmt.getStatementLabel();

        final boolean isThereAnyLabel = labelName != null;

        /* If there is no label detected there is nothing to do either */
        if (!isThereAnyLabel) {
            return null;
        }

        final Expression labelExpr = exprStmt.getExpression();

        return new Label(labelName, labelExpr);
    }

    /**
     * If you have a given list of {@link Group} you can apply a set
     * of transformations on certain statements within a given
     * group. Lets say we have group all statements in a method and we
     * would like to apply a transformation to statements in the group
     * identified with 'check':
     * <pre><code>
     * ['check': { Group group, ExpressionStatement stmt -> stmt }]
     * </code></pre>
     * This particular transformation will do nothing, it only returns the same statement.
     *
     * @param source the list of groups we want to operate over
     * @param mappings a map. Keys are label names and values are
     * closures transforming every statement of the identified group.
     * @return all statements (transformed and not transformed) returned in order.
     */
    public List<Statement> applyToStatementsByLabelFlatten(final List<Group> source, final Map<String,Closure<Statement>> mappings) {
        final List<Group> stmtGroupList       = applyToStatementsByLabel(source, mappings);
        final List<Statement> flattenStmtList = new ArrayList<Statement>();

        for (final Group group : stmtGroupList) {
            flattenStmtList.addAll(group.statements);
        }

        return flattenStmtList;
    }

    /**
     * If you have a given list of {@link Group} you can apply a set
     * of transformations on certain statements within a given
     * group. Lets say we have group all statements in a method and we
     * would like to apply a transformation to statements in the group
     * identified with 'check':
     * <pre><code>
     * ['check': { Group group, ExpressionStatement stmt -> stmt }]
     * </code></pre>
     * This particular transformation will do nothing, it only returns the same statement.
     *
     * @param source the list of groups we want to operate over
     * @param mappings a map. Keys are label names and values are
     * closures transforming every statement of the identified group.
     * @return all statements in their correspondent group.
     */
    public List<Group> applyToStatementsByLabel(final List<Group> source, final Map<String, Closure<Statement>> mappings) {
        return (List<Group>) inject(source, new ArrayList<>(), transformByLabelName(mappings));
    }

    private Closure<List<Group>> transformByLabelName(final Map<String, Closure<Statement>> mappings) {
        return new Closure<List<Group>>(null) {
            public List<Group> doCall(final List<Group> acc, final Group group)  {
                final Closure<Statement> trx = mappings.get(group.label.name);
                final List<Statement> source = group.statements;
                final List<Statement> destin = trx == null ? collect(source) : collect(source, trx.curry(group));

                acc.add(group.copyWithStatements(destin));

                return acc;
            }
        };
    }

}
