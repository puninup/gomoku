package si.gomoku.rules;

import si.gomoku.Direction;
import si.gomoku.game.Board;
import si.gomoku.game.Field;
import si.gomoku.game.Stone;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Tomasz Urbas
 */
public class ProRules implements RulesSet {

    private Board board;
    private int middleRow;
    private int middleColumn;

    public ProRules(Board board) {
        this.board = board;
        this.middleRow = Board.FIELDS_IN_ROW / 2;
        this.middleColumn = Board.FIELDS_IN_ROW / 2;
    }

    @Override
    public void performForMove(int move) {
        switch (move) {
            case 1:
                lockAllFields();
                unlockSquareAtMiddle(1);
                break;
            case 2:
                unlockSquareAtMiddle(3);
                lockSquareAtMiddle(1);
                break;
            case 3:
                unlockAllFields();
                lockSquareAtMiddle(5);
                break;
            case 4:
                unlockSquareAtMiddle(5);
        }
    }

    private void lockAllFields() {
        lockSquareAtMiddle(Board.FIELDS_IN_ROW);
    }

    private void lockSquareAtMiddle(int width) {
        int radius = width / 2;
        for (int row = middleRow - radius; row <= middleRow + radius; row++) {
            for (int column = middleColumn - radius; column <= middleColumn + radius; column++) {
                board.deactivateField(row, column);
            }
        }
    }

    private void unlockAllFields() {
        unlockSquareAtMiddle(Board.FIELDS_IN_ROW);
    }

    private void unlockSquareAtMiddle(int width) {
        int radius = width / 2;
        for (int row = middleRow - radius; row <= middleRow + radius; row++) {
            for (int column = middleColumn - radius; column <= middleColumn + radius; column++) {
                board.activateField(row, column);
            }
        }
    }

    @Override
    public boolean isWinning() {
        Field lastMove = board.getLastMove();
        return isWinningOnHorizontalLine(lastMove)
                || isWinningOnVerticalLine(lastMove)
                || isWinningOnDiagonalLeftLine(lastMove)
                || isWinningOnDiagonalRightLine(lastMove);
    }

    private boolean isWinningOnHorizontalLine(Field middleField) {
        return isWinningOnLine(middleField, Direction.LEFT, Direction.RIGHT);
    }

    private boolean isWinningOnVerticalLine(Field middleField) {
        return isWinningOnLine(middleField, Direction.TOP, Direction.BOTTOM);
    }

    private boolean isWinningOnDiagonalLeftLine(Field middleField) {
        return isWinningOnLine(middleField, Direction.TOP_LEFT, Direction.BOTTOM_RIGHT);
    }

    private boolean isWinningOnDiagonalRightLine(Field middleField) {
        return isWinningOnLine(middleField, Direction.TOP_RIGHT, Direction.BOTTOM_LEFT);
    }

    private boolean isWinningOnLine(Field middleField, Direction from, Direction to) {
        List<Field> leftFields = move(middleField, from, 5);
        Collections.reverse(leftFields);
        List<Field> rightFields = move(middleField, to, 5);
        Stream<Field> fields = Stream.of(leftFields, Collections.singletonList(middleField), rightFields)
                .flatMap(Collection::stream);
        return getLongestSequence(fields, middleField.getStone()) == 5;
    }

    private List<Field> move (Field from, Direction direction, int numberOfSteps) {
        List<Field> visitedFields = new LinkedList<>();
        while (numberOfSteps > 0) {
            from = doStep(from, direction);
            visitedFields.add(from);
            numberOfSteps--;
        }
        return visitedFields;
    }

    private Field doStep(Field from, Direction direction) {
        int row = from.getRow();
        int column = from.getColumn();
        return board.getField(row + direction.getRowStep(), column + direction.getColumnStep());
    }

    private int getLongestSequence(Stream<Field> fields, Stone stoneToSequence) {
        int longestSequence = 0;
        int currentSequence = 0;
        List<Stone> stoneSequence = fields.map(Field::getStone).collect(Collectors.toList());
        for (Stone stone : stoneSequence) {
            System.out.print(stone.name() + " ");
            if (stone == stoneToSequence) {
                currentSequence++;
            } else {
                longestSequence = (currentSequence > longestSequence) ? currentSequence : longestSequence;
                currentSequence = 0;
            }
        }
        System.out.println();
        return (currentSequence > longestSequence) ? currentSequence : longestSequence;
    }

    @Override
    public boolean isDraw() {
        return !board.isAnyEmptyField();
    }
}
