package si.gomoku.qualifiers;

import si.gomoku.game.Board;
import si.gomoku.game.Field;

import java.util.List;

/**
 * @author Tomasz Urbas
 */
public class ActiveFields implements QualityHeuristic {

    @Override
    public List<Field> qualify(List<Field> fields, Board board) {
        fields.removeIf(field -> !field.isActive());
        return fields.size() > 0 ? fields : board.getActiveFields();
    }

    @Override
    public int getPriority() {
        return 10;
    }
}
