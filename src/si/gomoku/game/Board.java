package si.gomoku.game;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import si.gomoku.Copyable;
import si.gomoku.Direction;
import si.gomoku.actions.PutStone;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tomasz Urbas
 */
public class Board implements Copyable<Board> {

    public static int DIMENSION = 15;
    public static int MIDDLE_ROW = DIMENSION / 2;
    public static int MIDDLE_COLUMN = MIDDLE_ROW;
    private static Field EMPTY_FIELD = Field.EMPTY_FIELD;

    private Matrix<Field> fields = new Matrix<>(DIMENSION);
    private Field lastMove = EMPTY_FIELD;

    public void setUpFields() {
        int totalFields = DIMENSION * DIMENSION;
        for (int i = 0; i < totalFields; i++) {
            int row = i / DIMENSION;
            int column = i % DIMENSION;
            fields.add(new Field(row, column));
        }
    }

    public void putStone(int row, int column, Stone stone) {
        Field field = fields.get(row, column);
        field.putStone(stone);
        lastMove = field;
    }

    public void pickUpStone(int row, int column) {
        Field field = fields.get(row, column);
        field.pickUpStone();
    }

    public Field getField(int row, int column) {
        if (row < 0 || row >= DIMENSION
                || column < 0 || column >= DIMENSION) {
            return EMPTY_FIELD;
        }
        return fields.get(row, column);
    }

    public void activateField(int row, int column) {
        fields.get(row, column).activate();
    }

    public void deactivateField(int row, int column) {
        fields.get(row, column).deactivate();
    }

    public List<Field> getActiveFields() {
        return fields.asList().stream()
                .filter(Field::isActive)
                .collect(Collectors.toList());
    }

    public List<Field> getFieldsWithStone(Stone stone) {
        return fields.asList().stream()
                .filter(field -> field.getStone() == stone)
                .collect(Collectors.toList());
    }

    public boolean isAnyEmptyField() {
        for (Field field : fields.asList()) {
            if (field.getStone() == Stone.NONE) {
                return true;
            }
        }
        return false;
    }

    public void reset() {
        fields.asList().forEach(Field::reset);
        lastMove = EMPTY_FIELD;
    }

    public Field getLastMove() {
        return lastMove;
    }

    public List<List<Stone>> getAllStoneSequences() {
        List<List<Stone>> sequencesOfStones = new LinkedList<>();
        for (int i = 0; i < DIMENSION; i++) {
            sequencesOfStones.add(getRowStoneSequence(i));
            sequencesOfStones.add(getColumnStoneSequence(i));
            sequencesOfStones.add(getLeftDiagonalStoneSequence(i, i));
            sequencesOfStones.add(getRightDiagonalStoneSequence(i, i));
        }
        return sequencesOfStones;
    }

    public List<Stone> getRowStoneSequence(int row) {
        List<Field> fieldSequence =  fields.getRow(row);
        return mapToStoneList(fieldSequence);
    }

    public List<Stone> getColumnStoneSequence(int column) {
        List<Field> fieldSequence =  fields.getColumn(column);
        return mapToStoneList(fieldSequence);
    }

    public List<Stone> getLeftDiagonalStoneSequence(int row, int column) {
        List<Field> fieldSequence =  fields.getLeftDiagonal(row, column);
        return mapToStoneList(fieldSequence);
    }

    public List<Stone> getRightDiagonalStoneSequence(int row, int column) {
        List<Field> fieldSequence =  fields.getRightDiagonal(row, column);
        return mapToStoneList(fieldSequence);
    }

    private List<Stone> mapToStoneList(List<Field> fields) {
        return fields.stream()
                .map(Field::getStone)
                .collect(Collectors.toList());
    }

    public List<Field> getAllNeighbors(int row, int column) {
        List<Field> neighbors = new LinkedList<>();
        for (Direction direction : Direction.values()) {
            fields.getNeighbor(row, column, direction)
                    .ifPresent(neighbors::add);
        }
        return neighbors;
    }

    public List<Stone> getAllNeighborStones(int row, int column) {
        List<Field> neighbors = getAllNeighbors(row, column);
        return neighbors.stream()
                .map(Field::getStone)
                .collect(Collectors.toList());
    }

    public List<MatrixPartIndex> getAllPartIndexes() {
        return fields.getAllPartIndexes();
    }

    public List<MatrixPartIndex> getPartIndexesFor(int row, int column) {
        return fields.getPartIndexesFor(row, column);
    }

    public List<Stone> getStoneSequence(MatrixPartIndex partIndex) {
        List<Stone> stones = new LinkedList<>();
        fields.getPart(partIndex)
                .forEach(part -> stones.add(part.getStone()));

        return stones;
    }

    public Board copy() {
        Board board = new Board();
        board.fields = fields.copy();
        return board;
    }

    // VIEW ----------------------------------
    private GridPane view;

    public void enableHumanInteraction(Stone stone) {
        for (Field field : getActiveFields()) {
            Node fieldView = field.getView();
            fieldView.setOnMouseClicked(new PutStone(this, field, stone));
        }
    }

    public void disableHumanInteraction() {
        for (Field field : fields.asList()) {
            Node fieldView = field.getView();
            fieldView.setOnMouseClicked(null);
        }
    }

    public void setUpView() {
        view = new GridPane();
        for (Field field : fields.asList()) {
            field.setUpView();
            view.add(field.getView(), field.getColumn(), field.getRow());
        }
    }

    public Node getView() {
        return view;
    }
}
