package asteroid.check;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.count;

import asteroid.A;
import groovy.lang.Closure;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;

import java.util.List;

/**
 * This checker makes sure a given {@link AnnotationNode} has only be applied once to
 * a given set of nodes of a given type.
 *
 * @param <T> The type of the nodes the annotation has been applied to.
 * @since 0.1.0
 */
public class AppliedOnceTo<T extends AnnotatedNode> implements Checker<AnnotationNode> {

    private final List<T> population;

    private AppliedOnceTo(final List<T> population) {
        this.population = population;
    }

    /**
     * Creates a checker of type {@link AppliedOnceTo} with the set of nodes a given annotation
     * could have been applied to
     *
     * @param <T> the type of instance expected for each item in the node list passed as argument
     * @param nodeList Set of nodes the annotation could be applied to
     * @return an instance of {@link AppliedOnceTo}
     */
    public static <T extends AnnotatedNode> AppliedOnceTo appliedOnceTo(final List<T> nodeList) {
        return new AppliedOnceTo(nodeList);
    }

    @Override
    public Result accepts(final AnnotationNode node) {
        Number howManyTimes = count(population.iterator(), new Closure<Boolean>(this) {
            @Override
            public Boolean call(Object item) {
                T sample = (T) item;
                List<AnnotationNode> annotations = sample.getAnnotations(node.getClassNode());

                return annotations.size() > 0;
            }
        });

        Boolean passes = howManyTimes.intValue() == 1;
        Result result = A.UTIL.createResult(passes, node, getErrorMessage(node));

        return result;
    }

    private String getErrorMessage(final AnnotationNode annotationNode) {
        return "Annotation " +
                annotationNode.getClassNode().getName() +
                " is repeated more than once in selected population";
    }
}
