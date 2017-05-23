package si.gomoku.heuristics;

import si.gomoku.heuristics.evaluators.GroupEvaluator;
import si.gomoku.heuristics.evaluators.SequenceEvaluator;

/**
 * @author Tomasz Urbas
 */
public class GroupSequenceHeuristic extends Heuristic {
    public GroupSequenceHeuristic() {
        addEvaluatorWithWeight(new GroupEvaluator(), 0.8);
        addEvaluatorWithWeight(new SequenceEvaluator(), 0.8);
    }
}
