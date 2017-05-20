package si.gomoku.game.rules;

import si.gomoku.game.Board;

/**
 * @author Tomasz Urbas
 */
public interface RulesSet {
    void performForMove(int move, Board board);
    boolean isWinning(Board board);
    boolean isDraw(Board board);
}
