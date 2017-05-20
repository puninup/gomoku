package si.gomoku.game;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import si.gomoku.Copyable;

/**
 * @author Tomasz Urbas
 */
public class Field implements Copyable<Field> {
    public static Field EMPTY_FIELD = new Field(-1, -1);

    private int row;
    private int column;
    private Stone stone;
    private boolean active;

    Field (int row, int column) {
        this.row = row;
        this.column = column;
        reset();
    }

    void putStone(Stone stone) {
        this.stone = stone;
        showStoneIfHasView();
    }

    void pickUpStone() {
        this.stone = Stone.NONE;
        showStoneIfHasView();
    }

    public void reset() {
        this.stone = Stone.NONE;
        this.active = true;
        showStoneIfHasView();
        showActiveIfHasView();
    }

    public Stone getStone() {
        return stone;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void activate() {
        active = true;
        showActiveIfHasView();
    }

    public void deactivate() {
        active = false;
        showInactiveIfHasView();
    }

    public boolean isActive() {
        return active && stone == Stone.NONE;
    }

    public Field copy() {
        Field field = new Field(row, column);
        field.stone = stone;
        field.active = active;
        return field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        return row == field.row && column == field.column;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }

    // VIEW --------------------------------
    private static int SIZE = 40;
    private static int STROKE_SIZE = 1;
    private static double STONE_RADIUS = SIZE * 0.35;

    private Group view;
    private Rectangle backgroundView;
    private Circle stoneView;

    private void showStoneIfHasView() {
        if (view != null) {
            stoneView.setFill(stone.getColor());
        }
    }

    private void showActiveIfHasView() {
        if (view != null) {
            backgroundView.setFill(Color.CHOCOLATE);
        }
    }

    private void showInactiveIfHasView() {
        if (view != null) {
            backgroundView.setFill(Color.BISQUE);
        }
    }

    public void setUpView() {
        backgroundView = new Rectangle();
        backgroundView.setFill(Color.CHOCOLATE);
        backgroundView.setWidth(SIZE - 2 * STROKE_SIZE);
        backgroundView.setHeight(SIZE - 2 * STROKE_SIZE);
        backgroundView.setStrokeWidth(STROKE_SIZE);
        backgroundView.setStroke(Color.BLACK);

        stoneView = new Circle(STONE_RADIUS, stone.getColor());
        stoneView.setCenterX(SIZE / 2);
        stoneView.setCenterY(SIZE / 2);

        view = new Group(backgroundView, stoneView);
    }

    public Node getView() {
        return view;
    }
}
