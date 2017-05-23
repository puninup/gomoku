package si.gomoku.heuristics;

import si.gomoku.heuristics.evaluators.GroupEvaluator;
import si.gomoku.heuristics.evaluators.NeighborsEvaluator;
import si.gomoku.heuristics.evaluators.SequenceEvaluator;

/**
 * @author Tomasz Urbas
 */
public class GroupNeighborsSequenceHeuristic extends Heuristic {
    public GroupNeighborsSequenceHeuristic() {
        addEvaluatorWithWeight(new GroupEvaluator(), 0.8);
        addEvaluatorWithWeight(new NeighborsEvaluator(), 0.8);
        addEvaluatorWithWeight(new SequenceEvaluator(), 1);
    }
}
