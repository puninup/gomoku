package si.gomoku.heuristics;

import si.gomoku.heuristics.evaluators.GroupEvaluator;

/**
 * @author Tomasz Urbas
 */
public class GroupHeuristic extends Heuristic {
    public GroupHeuristic() {
        addEvaluatorWithWeight(new GroupEvaluator(), 0.2);
    }
}
