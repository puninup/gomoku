package si.gomoku.qualifiers;

import si.gomoku.game.Board;
import si.gomoku.game.Field;
import si.gomoku.game.Stone;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tomasz Urbas
 */
public class NeighborsOfNeighborsFields implements QualityHeuristic {

    @Override
    public List<Field> qualify(List<Field> fields, Board board) {
        List<Field> uniqueNeighbors = fields.stream()
                .filter(field -> field.getStone() != Stone.NONE)
                .map(field -> board.getAllNeighbors(field.getRow(), field.getColumn()))
                .flatMap(Collection::stream)
                .distinct()
                .map(field -> board.getAllNeighbors(field.getRow(), field.getColumn()))
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

        return uniqueNeighbors.size() > 0 ? uniqueNeighbors : fields;
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
