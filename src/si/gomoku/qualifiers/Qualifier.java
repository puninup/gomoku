package si.gomoku.qualifiers;

import si.gomoku.game.Board;
import si.gomoku.game.Field;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tomasz Urbas
 */
public class Qualifier {

    private List<QualityHeuristic> heuristics = new LinkedList<>();

    public Qualifier() {
        addHeuristic(new ActiveFields());
    }

    public List<Field> getPreferableFields(Board board) {
        List<Field> fields = new LinkedList<>(board.getAllFields());
        for (QualityHeuristic heuristic : heuristics) {
            fields = heuristic.qualify(fields, board);
        }
        return fields;
    }

    public void addHeuristic(QualityHeuristic newHeuristic) {
        heuristics.removeIf(h -> h.getPriority() == newHeuristic.getPriority());

        for (int i = 0; i < heuristics.size(); i++) {
            if (heuristics.get(i).getPriority() > newHeuristic.getPriority()) {
                heuristics.add(i, newHeuristic);
                return;
            }
        }
        heuristics.add(newHeuristic);
    }

    public void removeHeuristic(QualityHeuristic heuristic) {
        heuristics.removeIf(h -> h.getPriority() == heuristic.getPriority());
    }
}
