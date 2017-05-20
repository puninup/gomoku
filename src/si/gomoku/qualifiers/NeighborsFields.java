package si.gomoku.qualifiers;

import si.gomoku.game.Board;
import si.gomoku.game.Field;
import si.gomoku.game.Stone;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Tomasz Urbas
 */
public class NeighborsFields implements QualityHeuristic {

    @Override
    public List<Field> qualify(List<Field> fields, Board board) {
        Set<Field> uniqueNeighbors = new HashSet<>();
        fields.stream()
                .filter(field -> field.getStone() != Stone.NONE)
                .forEach(field -> {
                    List<Field> neighbors = board.getAllNeighbors(field.getRow(), field.getColumn());
                    uniqueNeighbors.addAll(neighbors);
                });

        return uniqueNeighbors.size() > 0 ? new ArrayList<>(uniqueNeighbors) : fields;
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
