package si.gomoku.qualifiers;

import si.gomoku.game.Board;
import si.gomoku.game.Field;

import java.util.List;

/**
 * @author Tomasz Urbas
 */
public interface QualityHeuristic {
    List<Field> qualify(List<Field> fields, Board board);
    int getPriority();
}
