package si.gomoku.heuristics;

import si.gomoku.heuristics.evaluators.GroupEvaluator;
import si.gomoku.heuristics.evaluators.NeighborsEvaluator;

/**
 * @author Tomasz Urbas
 */
public class SequenceNeighborsHeuristic extends Heuristic {
    public SequenceNeighborsHeuristic() {
        addEvaluatorWithWeight(new GroupEvaluator(), 0.5);
        addEvaluatorWithWeight(new NeighborsEvaluator(), 0.5);
    }
}
