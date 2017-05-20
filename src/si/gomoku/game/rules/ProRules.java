package si.gomoku.game.rules;

import si.gomoku.Direction;
import si.gomoku.game.Board;
import si.gomoku.game.Field;
import si.gomoku.game.Stone;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Tomasz Urbas
 */
public class ProRules implements RulesSet {

    @Override
    public void performForMove(int move, Board board) {
        switch (move) {
            case 1:
                lockAllFields(board);
                unlockSquareAtMiddle(1, board);
                break;
            case 2:
                unlockSquareAtMiddle(3, board);
                lockSquareAtMiddle(1, board);
                break;
            case 3:
                unlockAllFields(board);
                lockSquareAtMiddle(5, board);
                break;
            case 4:
                unlockSquareAtMiddle(5, board);
        }
    }

    private void lockAllFields(Board board) {
        lockSquareAtMiddle(Board.DIMENSION, board);
    }

    private void lockSquareAtMiddle(int width, Board board) {
        int middleRow = Board.MIDDLE_ROW;
        int middleColumn = Board.MIDDLE_COLUMN;
        int radius = width / 2;
        for (int row = middleRow - radius; row <= middleRow + radius; row++) {
            for (int column = middleColumn - radius; column <= middleColumn + radius; column++) {
                board.deactivateField(row, column);
            }
        }
    }

    private void unlockAllFields(Board board) {
        unlockSquareAtMiddle(Board.DIMENSION, board);
    }

    private void unlockSquareAtMiddle(int width, Board board) {
        int middleRow = Board.MIDDLE_ROW;
        int middleColumn = Board.MIDDLE_COLUMN;
        int radius = width / 2;
        for (int row = middleRow - radius; row <= middleRow + radius; row++) {
            for (int column = middleColumn - radius; column <= middleColumn + radius; column++) {
                board.activateField(row, column);
            }
        }
    }

    @Override
    public boolean isWinning(Board board) {
        Field lastMove = board.getLastMove();
        return horizontalLineSequenceValue(lastMove, board) == 5
                || verticalLineSequenceValue(lastMove, board) == 5
                || diagonalLeftLineSequenceValue(lastMove, board) == 5
                || diagonalRightLineSequenceValue(lastMove, board) == 5;
    }

    private int horizontalLineSequenceValue(Field middleField, Board board) {
        return sequenceValueOnLine(middleField, Direction.LEFT, Direction.RIGHT, board);
    }

    private int verticalLineSequenceValue(Field middleField, Board board) {
        return sequenceValueOnLine(middleField, Direction.TOP, Direction.BOTTOM, board);
    }

    private int diagonalLeftLineSequenceValue(Field middleField, Board board) {
        return sequenceValueOnLine(middleField, Direction.TOP_LEFT, Direction.BOTTOM_RIGHT, board);
    }

    private int diagonalRightLineSequenceValue(Field middleField, Board board) {
        return sequenceValueOnLine(middleField, Direction.TOP_RIGHT, Direction.BOTTOM_LEFT, board);
    }

    private int sequenceValueOnLine(Field middleField, Direction from, Direction to, Board board) {
        List<Field> leftFields = move(middleField, from, 5, board);
        Collections.reverse(leftFields);
        List<Field> rightFields = move(middleField, to, 5, board);
        Stream<Field> fields = Stream.of(leftFields, Collections.singletonList(middleField), rightFields)
                .flatMap(Collection::stream);
        return getLongestSequence(fields, middleField.getStone());
    }

    private List<Field> move(Field from, Direction direction, int numberOfSteps, Board board) {
        List<Field> visitedFields = new LinkedList<>();
        while (numberOfSteps > 0) {
            from = doStep(from, direction, board);
            visitedFields.add(from);
            numberOfSteps--;
        }
        return visitedFields;
    }

    private static Field doStep(Field from, Direction direction, Board board) {
        int row = from.getRow();
        int column = from.getColumn();
        return board.getField(row + direction.getRowStep(), column + direction.getColumnStep());
    }

    private static int getLongestSequence(Stream<Field> fields, Stone stoneToSequence) {
        int longestSequence = 0;
        int currentSequence = 0;
        List<Stone> stoneSequence = fields.map(Field::getStone).collect(Collectors.toList());
        for (Stone stone : stoneSequence) {
            if (stone == stoneToSequence) {
                currentSequence++;
            } else {
                longestSequence = (currentSequence > longestSequence) ? currentSequence : longestSequence;
                currentSequence = 0;
            }
        }
        return (currentSequence > longestSequence) ? currentSequence : longestSequence;
    }

    @Override
    public boolean isDraw(Board board) {
        return !board.isAnyEmptyField();
    }
}
