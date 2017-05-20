package si.gomoku.evaluators;

import si.gomoku.game.Board;
import si.gomoku.game.Stone;

/**
 * @author Tomasz Urbas
 */
public interface Evaluator {
    void renew(Board board, Stone stone);
    int evaluate();
    void updateValueFor(int row, int column);
}
