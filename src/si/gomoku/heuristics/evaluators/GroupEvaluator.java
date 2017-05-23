package si.gomoku.heuristics.evaluators;

import si.gomoku.game.Board;
import si.gomoku.game.MatrixPartIndex;
import si.gomoku.game.Stone;

import java.util.*;

/**
 * @author Tomasz Urbas
 */
public class GroupEvaluator implements Evaluator {
    private static Map<Integer, Integer> pointsOfSequence = new HashMap<>();
    static {
        pointsOfSequence.put(0, 0);
        pointsOfSequence.put(1, 1);
        pointsOfSequence.put(2, 4);
        pointsOfSequence.put(3, 27);
        pointsOfSequence.put(4, 100);
        pointsOfSequence.put(5, 10000);
    }

    private Board board;
    private Stone stone;
    private Map<MatrixPartIndex, Integer> valueOfPartIndex;
    private Deque<Map<MatrixPartIndex, Integer>> updateDeque;


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

    private void updatePartValue(MatrixPartIndex partIndex) {
        List<Stone> sequence = board.getStoneSequence(partIndex);
        int value = evaluateSequence(sequence);
        valueOfPartIndex.put(partIndex, value);
    }

    private int evaluateSequence(List<Stone> sequence) {
        int value = 0;
        value += evaluateSequence(sequence, stone);
        value -= evaluateSequence(sequence, stone.opposite());
        return value;
    }

    private int evaluateSequence(List<Stone> sequence, Stone evaluatedStone) {
        if (sequence.size() < 5) {
            return 0;
        }

        int value = 0;
        List<Stone> possibleGroup = new LinkedList<>();
        for (int i = 0; i < sequence.size(); i++) {
            Stone stone = sequence.get(i);
            if (stone == evaluatedStone.opposite()) {
                possibleGroup.clear();
                continue;
            }
            possibleGroup.add(stone);
            if(possibleGroup.size() < 5) {
                continue;
            } else if(possibleGroup.size() > 5) {
                possibleGroup.remove(0);
            }

            int counter = 0;
            for (int j = 0; j < 5 ; j++) {
                Stone sequenceStone = possibleGroup.get(j);
                if (sequenceStone == evaluatedStone) {
                    counter++;
                }
            }
            value += pointsOfSequence.get(counter);
        }
        return value;
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
