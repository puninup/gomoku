package si.gomoku.heuristics;

import si.gomoku.game.Board;
import si.gomoku.game.Field;
import si.gomoku.game.Stone;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Tomasz Urbas
 */
public class FieldsNearby implements QualityHeuristic {

    @Override
    public List<Field> getPreferableFields(Board board) {
        List<Field> fields = new LinkedList<>();
        fields.addAll(board.getFieldsWithStone(Stone.DARK));
        fields.addAll(board.getFieldsWithStone(Stone.LIGHT));

        Set<Field> uniqueNeighbors = new HashSet<>();
        fields.forEach(field ->
                uniqueNeighbors.addAll(board.getAllNeighbors(field.getRow(), field.getColumn())));

        List<Field> preferableFields = uniqueNeighbors.stream()
                .filter(Field::isActive)
                .collect(Collectors.toList());
        return preferableFields.size() > 0 ? preferableFields : board.getActiveFields();
    }
}
