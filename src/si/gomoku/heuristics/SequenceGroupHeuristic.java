package si.gomoku.heuristics;

import si.gomoku.heuristics.evaluators.GroupEvaluator;
import si.gomoku.heuristics.evaluators.SequenceEvaluator;

/**
 * @author Tomasz Urbas
 */
public class SequenceGroupHeuristic extends Heuristic {
    public SequenceGroupHeuristic() {
        addEvaluatorWithWeight(new SequenceEvaluator(), 0.8);
        addEvaluatorWithWeight(new GroupEvaluator(), 0.8);
    }
}
