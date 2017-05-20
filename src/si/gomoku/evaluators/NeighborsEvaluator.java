package si.gomoku.evaluators;

import si.gomoku.game.Board;
import si.gomoku.game.Field;
import si.gomoku.game.Stone;

import java.util.List;

/**
 * @author Tomasz Urbas
 */
public class NeighborsEvaluator implements Evaluator {

    private Board board;
    private Stone stone;

    @Override
    public void renew(Board board, Stone stone) {
        this.board = board;
        this.stone = stone;
    }

    @Override
    public int evaluate() {
        int value = 0;
        value += evaluateNeighbors(board, stone);
        value -= evaluateNeighbors(board, stone.oppositeStone()) * 2;
        return value;
    }

    private int evaluateNeighbors(Board board, Stone myStone) {
        List<Field> fields = board.getFieldsWithStone(myStone);

        int value = 0;
        for (Field field : fields) {
            List<Stone> neighbors = board.getAllNeighborStones(field.getRow(), field.getColumn());
            value += evaluate(neighbors, myStone);
        }
        return value;
    }

    private int evaluate(List<Stone> neighbors, Stone myStone) {
        return (int) neighbors.stream()
                .filter(stone -> stone == myStone)
                .count();
    }

    @Override
    public void updateValueFor(int row, int column) {
    }

    @Override
    public void revertUpdate() {
    }
}
