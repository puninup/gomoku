package si.gomoku.heuristics.evaluators;

import si.gomoku.game.Board;
import si.gomoku.game.Field;
import si.gomoku.game.Stone;

import java.util.*;

/**
 * @author Tomasz Urbas
 */
public class NeighborsEvaluator implements Evaluator {
    private Board board;
    private Stone stone;
    private Map<Field, Integer> valueOfField;
    private Deque<Map<Field, Integer>> updateDeque;

    @Override
    public void renew(Board board, Stone stone) {
        this.board = board;
        this.stone = stone;
        this.valueOfField = new HashMap<>();
        this.updateDeque = new ArrayDeque<>();
        initValues();
    }

    private void initValues() {
        List<Field> fields = board.getFieldsWithStone(stone);
        fields.addAll(board.getFieldsWithStone(stone.opposite()));
        fields.forEach(this::updateValueFor);
    }

    @Override
    public void updateValueFor(int row, int column) {
        Map<Field, Integer> currentState = new HashMap<>();

        Field currentField = board.getField(row, column);
        List<Field> fields = board.getAllNeighbors(row, column);
        fields.add(currentField);

        for (Field field : fields) {
            currentState.put(field, valueOfField.get(field));
            updateValueFor(field);
        }
        updateDeque.push(currentState);
    }

    private void updateValueFor(Field field) {
        int value = evaluateField(field);
        valueOfField.put(field, value);
    }

    private int evaluateField(Field field) {
        Stone stone = field.getStone();
        List<Stone> neighbors = board.getAllNeighborStones(field.getRow(), field.getColumn());

        int value = 0;
        for (Stone neighborStone : neighbors) {
            if (neighborStone == stone) {
                value += 1;
            } else if (neighborStone == Stone.NONE) {
                value += 4;
            }
        }
        return value;
    }

    @Override
    public int evaluate() {
        int value = 0;
        value += getValueFor(stone);
        value -= getValueFor(stone.opposite()) * 2;
        return value;
    }

    private int getValueFor(Stone stone) {
        return valueOfField.keySet().stream()
                .filter(field -> field.getStone() == stone)
                .map(field -> valueOfField.get(field))
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public void revertUpdate() {
        Map<Field, Integer> previousState = updateDeque.pop();
        valueOfField.putAll(previousState);
    }
}
