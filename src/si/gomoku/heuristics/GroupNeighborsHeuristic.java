package si.gomoku.heuristics;

import si.gomoku.heuristics.evaluators.NeighborsEvaluator;
import si.gomoku.heuristics.evaluators.SequenceEvaluator;

/**
 * @author Tomasz Urbas
 */
public class GroupNeighborsHeuristic extends Heuristic {
    public GroupNeighborsHeuristic() {
        addEvaluatorWithWeight(new SequenceEvaluator(), 0.8);
        addEvaluatorWithWeight(new NeighborsEvaluator(), 0.6);
    }
}
