package si.gomoku.heuristics;

import si.gomoku.heuristics.evaluators.GroupEvaluator;
import si.gomoku.heuristics.evaluators.NeighborsEvaluator;
import si.gomoku.heuristics.evaluators.SequenceEvaluator;

/**
 * @author Tomasz Urbas
 */
public class TestHeuristic extends Heuristic {
    public TestHeuristic(double group, double neighbors, double sequence) {
        addEvaluatorWithWeight(new GroupEvaluator(), group);
        addEvaluatorWithWeight(new NeighborsEvaluator(), neighbors);
        addEvaluatorWithWeight(new SequenceEvaluator(), sequence);
    }
}
