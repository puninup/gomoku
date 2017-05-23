package si.gomoku.heuristics.evaluators;

import si.gomoku.game.Board;
import si.gomoku.game.MatrixPartIndex;
import si.gomoku.game.Stone;

import java.util.*;

/**
 * @author Tomasz Urbas
 */
public class SequenceEvaluator implements Evaluator {
    private static Map<Integer, Integer> pointsOfSequence = new HashMap<>();
    static {
        pointsOfSequence.put(0, 0);
        pointsOfSequence.put(1, 1);
        pointsOfSequence.put(2, 3);
        pointsOfSequence.put(3, 27);
        pointsOfSequence.put(4, 100);
        pointsOfSequence.put(5, 10000);
        for (int i = 6; i < Board.DIMENSION; i++) {
            pointsOfSequence.put(i, 0);
        }
    }

    private Board board;
    private Stone stone;
    private Map<MatrixPartIndex, Integer> valueOfPartIndex;
    private Deque<Map<MatrixPartIndex, Integer>> updateDeque;

    @Override
    public void updateValueFor(int row, int column) {
        Map<MatrixPartIndex, Integer> currentState = new HashMap<>();
        List<MatrixPartIndex> partIndexes = board.getPartIndexesFor(row, column);
        for(MatrixPartIndex partIndex : partIndexes) {
            currentState.put(partIndex, valueOfPartIndex.get(partIndex));
            updatePartValue(partIndex);
        }
        updateDeque.push(currentState);
    }

    @Override
    public void renew(Board board, Stone stone) {
        this.board = board;
        this.stone = stone;
        this.valueOfPartIndex = new HashMap<>();
        this.updateDeque = new ArrayDeque<>();
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

        int opponentsValue = evaluateSequence(sequence, stone.opposite());
        value -= opponentsValue * opponentsValue;
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
            } else if (stone == evaluatedStone.opposite()) {
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

    @Override
    public int evaluate() {
        return valueOfPartIndex.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public void revertUpdate() {
        Map<MatrixPartIndex, Integer> previousState = updateDeque.pop();
        valueOfPartIndex.putAll(previousState);
    }
}
