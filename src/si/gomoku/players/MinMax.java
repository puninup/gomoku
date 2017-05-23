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
    private int moveNumber;

    MinMax(Board board, Stone stone, RulesSet rules) {
        super(board, stone, rules);
    }

    @Override
    public void move(int moveNumber) {
        bestMove = Field.EMPTY_FIELD;
        this.moveNumber = moveNumber;
        Board board = this.board.copy();
        heuristic.renewWith(board, stone);
        minMax(0, board);

        if (stopped && !endOfTime) {
            return;
        }

        this.board.putStone(bestMove.getRow(), bestMove.getColumn(), stone);
    }

    private int minMax(int depth, Board board) {
        if (stopped) {
            return Integer.MIN_VALUE;
        }
        if (isLeaf(depth, board)) {
            return heuristic.evaluate();
        }

        Level level = (depth % 2 == 0) ? Level.MAX : Level.MIN;
        int best = level.getWorstValue();
        rules.performForMove(moveNumber + depth, board);
        List<Field> fields = qualifier.getPreferableFields(board);
        for (Field field : fields) {
            board.putStone(field.getRow(), field.getColumn(), level.getStone(stone));
            heuristic.updateValueFor(field.getRow(), field.getColumn());

            int current = minMax(depth + 1, board);

            board.pickUpStone(field.getRow(), field.getColumn());
            heuristic.revertUpdate();

            if (level.isBetter(current, best)) {
                best = current;
                if (depth == 0) {
                    bestMove = field;
                }
            }
        }
        return best;
    }
}
