package si.gomoku.evaluators;

import si.gomoku.game.Board;
import si.gomoku.game.MatrixPartIndex;
import si.gomoku.game.Stone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tomasz Urbas
 */
public class GroupEvaluator implements Evaluator {
    private static Map<Integer, Integer> pointsOfSequence = new HashMap<>();
    static {
        pointsOfSequence.put(0, 0);
        pointsOfSequence.put(1, 1);
        pointsOfSequence.put(2, 5);
        pointsOfSequence.put(3, 50);
        pointsOfSequence.put(4, 1000);
        pointsOfSequence.put(5, 100000);
        for (int i = 6; i < Board.DIMENSION; i++) {
            pointsOfSequence.put(i, 0);
        }
    }

    private Board board;
    private Stone stone;
    private Map<MatrixPartIndex, Integer> valueOfPartIndex;

    public void renew(Board board, Stone stone) {
        this.board = board;
        this.stone = stone;
        this.valueOfPartIndex = new HashMap<>();
        initValues();
    }

    private void initValues() {
        valueOfPartIndex = new HashMap<>();
        List<MatrixPartIndex> partIndexes = board.getAllPartIndexes();
        for (MatrixPartIndex partIndex : partIndexes) {
            updatePartValue(partIndex);
        }
    }

    private void updatePartValue(MatrixPartIndex partIndex) {
        List<Stone> sequence = board.getStoneSequence(partIndex);
        int value = evaluateSequence(sequence);
        valueOfPartIndex.put(partIndex, value);
    }

    private int evaluateSequence(List<Stone> sequence) {
        int value = 0;
        value += evaluateSequence(sequence, stone);
        value -= evaluateSequence(sequence, stone.oppositeStone()) * 2;
        return value;
    }

    private int evaluateSequence(List<Stone> sequence, Stone evaluatedStone) {
        if (sequence.size() < 5) {
            return 0;
        }

        int value = 0;
        int length = 0;
        int possibleLength = 0;
        for (Stone stone : sequence) {
            if (stone == evaluatedStone) {
                length++;
                possibleLength++;
            } else if (stone == evaluatedStone.oppositeStone()) {
                value += calculateValue(length, possibleLength);
                length = 0;
                possibleLength = 0;
            } else {
                value += calculateValue(length, possibleLength);
                length = 0;
                possibleLength++;
            }
        }

        value += calculateValue(length, possibleLength);
        return value;
    }

    private int calculateValue(int length, int possibleLength) {
        if (possibleLength >= 5) {
            return pointsOfSequence.get(length);
        }
        return 0;
    }

    public int evaluate() {
        return valueOfPartIndex.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public void updateValueFor(int row, int column) {
        List<MatrixPartIndex> partIndexes = board.getPartIndexesFor(row, column);
        for(MatrixPartIndex partIndex : partIndexes) {
            updatePartValue(partIndex);
        }
    }
}
