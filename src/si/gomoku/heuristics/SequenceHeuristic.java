package si.gomoku.heuristics;

import si.gomoku.heuristics.evaluators.SequenceEvaluator;

/**
 * @author Tomasz Urbas
 */
public class SequenceHeuristic extends Heuristic {
    public SequenceHeuristic() {
        addEvaluator(new SequenceEvaluator());
    }
}
