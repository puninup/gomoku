package si.gomoku.components;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import si.gomoku.actions.PutStone;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tomasz Urbas
 */
public class Board {

    public static int FIELDS_IN_ROW = 15;
    private static Field EMPTY_FIELD = new Field(-1, -1);

    private ArrayList<Field> fields;
    private Field lastMove = EMPTY_FIELD;

    Board() {
        setUpFields();
    }

    private void setUpFields() {
        int totalFields = FIELDS_IN_ROW * FIELDS_IN_ROW;
        fields = new ArrayList<>(totalFields);
        for (int i = 0; i < totalFields; i++) {
            int row = i / FIELDS_IN_ROW;
            int column = i % FIELDS_IN_ROW;
            fields.add(new Field(row, column));
        }
    }

    public void putStone(int row, int column, Stone stone) {
        int fieldIndex = getFieldIndex(row, column);
        Field field = fields.get(fieldIndex);
        field.putStone(stone);
        lastMove = field;
    }

    public Field getField(int row, int column) {
        if (row < 0 || row >= FIELDS_IN_ROW
                || column < 0 || column >= FIELDS_IN_ROW) {
            return EMPTY_FIELD;
        }
        int fieldIndex = getFieldIndex(row, column);
        return fields.get(fieldIndex);
    }

    public void activateField(int row, int column) {
        int fieldIndex = getFieldIndex(row, column);
        fields.get(fieldIndex).activate();
    }

    public void deactivateField(int row, int column) {
        int fieldIndex = getFieldIndex(row, column);
        fields.get(fieldIndex).deactivate();
    }

    public List<Field> getActiveFields() {
        return fields.stream()
                .filter(Field::isActive)
                .collect(Collectors.toList());
    }

    public boolean isAnyEmptyField() {
        for (Field field : fields) {
            if (field.getStone() == Stone.NONE) {
                return true;
            }
        }
        return false;
    }

    public Field getLastMove() {
        return lastMove;
    }

    private int getFieldIndex(int row, int column) {
        return row * FIELDS_IN_ROW + column;
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
        for (Field field : fields) {
            Node fieldView = field.getView();
            fieldView.setOnMouseClicked(null);
        }
    }

    public void setUpView() {
        view = new GridPane();
        for (Field field : fields) {
            field.setUpView();
            view.add(field.getView(), field.getRow(), field.getColumn());
        }
    }

    public Node getView() {
        return view;
    }
}
