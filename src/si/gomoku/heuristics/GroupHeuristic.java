package si.gomoku.heuristics;

import si.gomoku.evaluators.GroupEvaluator;

/**
 * @author Tomasz Urbas
 */
public class GroupHeuristic extends Heuristic {
    public GroupHeuristic() {
        addEvaluator(new GroupEvaluator());
    }
}
