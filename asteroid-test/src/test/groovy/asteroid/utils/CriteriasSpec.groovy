package asteroid.utils

import spock.lang.Specification

import asteroid.A

class CriteriasSpec extends Specification {

    void 'byExprMethodCallByArgs: filter method calls by args classes'() {
        given: 'a list of expressions'
        def expressions = [
            A.EXPR.callX(A.EXPR.constX('word'),
                         'substring',
                         A.EXPR.constX(1),
                         A.EXPR.constX(2)),
            A.EXPR.callX(A.EXPR.constX('word'),
                         'size')
        ]

        when: 'filtering by arg types'
        def criteria = A.CRITERIA.byExprMethodCallByArgs(Integer, Integer)
        def result = expressions.findAll(criteria)

        then: 'we are getting just one'
        result.size() == 1

        and: 'and it should be the substring call'
        result.find().getMethodAsString() == 'substring'
    }

    void 'byExprMethodCallByArgs: filter method calls by args class nodes'() {
        given: 'a list of expressions'
        def expressions = [
            A.EXPR.callX(A.EXPR.constX('word'),
                         'substring',
                         A.EXPR.constX(1),
                         A.EXPR.constX(2)),
            A.EXPR.callX(A.EXPR.constX('word'),
                         'size')
        ]

        when: 'filtering by arg types'
        def criteria = A.CRITERIA
            .byExprMethodCallByArgs(A.NODES.clazz(Integer).build(),
                                    A.NODES.clazz(Integer).build())
        def result = expressions.findAll(criteria)

        then: 'we are getting just one'
        result.size() == 1

        and: 'and it should be the substring call'
        result.find().getMethodAsString() == 'substring'
    }

    void 'byExprMethodCallByArgs: same parameter type but different names'() {
        given: 'a list of expressions'
        def expressions = [
            A.EXPR.callX(A.EXPR.constX('word'), 'methodA', A.EXPR.constX(1)),
            A.EXPR.callX(A.EXPR.constX('word'), 'methodB', A.EXPR.constX(1)),
            A.EXPR.callX(A.EXPR.constX('word'), 'methodC', A.EXPR.constX(1))
        ]

        when: 'filtering by arg types'
        def criteria = A.CRITERIA.and(
            A.CRITERIA.byExprMethodCallByName('methodA'),
            A.CRITERIA.byExprMethodCallByArgs(Integer))

        def result = expressions.findAll(criteria)

        then: 'we are getting just one'
        result.size() == 1

        and: 'and it should be the substring call'
        result.find().getMethodAsString() == 'methodA'
    }

    void 'byExprMethodCallByArgs: find overwritten method'() {
        given: 'a list of expressions'
        def expressions = [
            A.EXPR.callX(A.EXPR.constX('word'), 'substring', A.EXPR.constX(1), A.EXPR.constX(2)),
            A.EXPR.callX(A.EXPR.constX('word'), 'substring', A.EXPR.constX(1)),
            A.EXPR.callX(A.EXPR.constX('word'), 'size')
        ]

        when: 'filtering by arg types'
        def criteria = A.CRITERIA.and(
            A.CRITERIA.byExprMethodCallByName('substring'),
            A.CRITERIA.byExprMethodCallByArgs(Integer,Integer))

        def result = expressions.findAll(criteria)

        then: 'we are getting just one'
        result.size() == 1

        and: 'and it should be the substring call'
        result.find().getMethodAsString() == 'substring'
    }
}
