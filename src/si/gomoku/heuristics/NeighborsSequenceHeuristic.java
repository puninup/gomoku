package si.gomoku.heuristics;

import si.gomoku.heuristics.evaluators.NeighborsEvaluator;
import si.gomoku.heuristics.evaluators.SequenceEvaluator;

/**
 * @author Tomasz Urbas
 */
public class NeighborsSequenceHeuristic extends Heuristic {
    public NeighborsSequenceHeuristic() {
        addEvaluatorWithWeight(new NeighborsEvaluator(), 0.6);
        addEvaluatorWithWeight(new SequenceEvaluator(), 0.8);
    }
}
