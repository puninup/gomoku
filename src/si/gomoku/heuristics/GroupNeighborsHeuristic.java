package si.gomoku.heuristics;

import si.gomoku.heuristics.evaluators.GroupEvaluator;
import si.gomoku.heuristics.evaluators.NeighborsEvaluator;

/**
 * @author Tomasz Urbas
 */
public class GroupNeighborsHeuristic extends Heuristic {
    public GroupNeighborsHeuristic() {
        addEvaluatorWithWeight(new GroupEvaluator(), 0.3);
        addEvaluatorWithWeight(new NeighborsEvaluator(), 0.4);
    }
}
