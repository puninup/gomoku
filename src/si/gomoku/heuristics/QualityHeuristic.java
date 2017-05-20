package si.gomoku.heuristics;

import si.gomoku.game.Board;
import si.gomoku.game.Field;

import java.util.List;

/**
 * @author Tomasz Urbas
 */
public interface QualityHeuristic {
    List<Field> getPreferableFields(Board board);
}
