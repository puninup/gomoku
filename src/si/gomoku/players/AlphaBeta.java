package si.gomoku.players;

import si.gomoku.game.Board;
import si.gomoku.game.Field;
import si.gomoku.game.Stone;
import si.gomoku.game.rules.RulesSet;

import java.util.List;

/**
 * @author Tomasz Urbas
 */
public class AlphaBeta extends PlayerAI {

    private Field bestMove;
    private int moveNumber;

    AlphaBeta(Board board, Stone stone, RulesSet rules) {
        super(board, stone, rules);
    }

    @Override
    public void move(int moveNumber) {
        bestMove = Field.EMPTY_FIELD;
        this.moveNumber = moveNumber;
        Board board = this.board.copy();
        heuristic.renewWith(board, stone);
        alphaBeta(0, Integer.MIN_VALUE, Integer.MAX_VALUE, board);

        if (stopped && !endOfTime) {
            return;
        }

        this.board.putStone(bestMove.getRow(), bestMove.getColumn(), stone);
    }

    private int alphaBeta(int depth, int alpha, int beta, Board board) {
        if (stopped) {
            return Integer.MIN_VALUE;
        }
        if (isLeaf(depth, board)) {
            numberOfCalculations++;
            return heuristic.evaluate();
        }

        Level level = (depth % 2 == 0) ? Level.MAX : Level.MIN;
        rules.performForMove(moveNumber + depth, board);
        List<Field> fields = qualifier.getPreferableFields(board);
        if (depth == 0) {
            bestMove = fields.get(0);
        }
        for (Field field : fields) {
            board.putStone(field.getRow(), field.getColumn(), level.getStone(stone));
            heuristic.updateValueFor(field.getRow(), field.getColumn());

            int current = alphaBeta(depth + 1, beta, alpha, board);

            board.pickUpStone(field.getRow(), field.getColumn());
            heuristic.revertUpdate();

            if (level.isBetterOrEqual(current, beta)) {
                return current;
            } else if (level.isBetter(current, alpha)) {
                alpha = current;
                if (depth == 0) {
                    bestMove = field;
                }
            }
        }
        return alpha;
    }
}
