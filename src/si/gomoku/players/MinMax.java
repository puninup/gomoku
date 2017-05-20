package si.gomoku.players;

import si.gomoku.game.Board;
import si.gomoku.game.Field;
import si.gomoku.game.Stone;
import si.gomoku.game.rules.RulesSet;

import java.util.List;

/**
 * @author Tomasz Urbas
 */
public class MinMax extends PlayerAI {
    private Field bestMove;

    MinMax(Board board, Stone stone, RulesSet rules) {
        super(board, stone, rules);
    }

    @Override
    public void move(int moveNumber) {
        bestMove = Field.EMPTY_FIELD;
        Board board = this.board.copy();
        heuristic.renewWith(board, stone);
        minMax(Level.MAX, 1, moveNumber, board);

        if (stopped && !endOfTime) {
            return;
        }

        this.board.putStone(bestMove.getRow(), bestMove.getColumn(), stone);
    }

    private int minMax(Level level, int depth, int moveNumber, Board board) {
        if (stopped) {
            return Integer.MIN_VALUE;
        }
        if (isLeaf(depth, board)) {
            return heuristic.evaluate();
        }

        int best = level.getWorstValue();
        rules.performForMove(moveNumber, board);
        List<Field> fields = qualifier.getPreferableFields(board);
        for (Field field : fields) {
            board.putStone(field.getRow(), field.getColumn(), level.getStone(stone));
            heuristic.updateValueFor(field.getRow(), field.getColumn());
            int current = minMax(level.opposite(), depth + 1, moveNumber + 1, board);
            board.pickUpStone(field.getRow(), field.getColumn());
            heuristic.revertUpdate();
            if (level.isBetter(current, best)) {
                best = current;
                if (depth == 1) {
                    bestMove = field;
                }
            }
        }
        return best;
    }
}
