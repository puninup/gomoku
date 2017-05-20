package si.gomoku.heuristics;

import si.gomoku.evaluators.NeighborsEvaluator;

/**
 * @author Tomasz Urbas
 */
public class NeighborsHeuristic extends Heuristic {
    public NeighborsHeuristic() {
        addEvaluator(new NeighborsEvaluator());
    }
}
