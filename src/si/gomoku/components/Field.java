package si.gomoku.components;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * @author Tomasz Urbas
 */
public class Field {

    private int row;
    private int column;
    private Stone stone = Stone.NONE;
    private boolean active = false;

    Field (int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void putStone(Stone stone) {
        this.stone = stone;
        this.active = false;
        showStoneIfHasView();
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
        if (stone == Stone.NONE) {
            active = true;
        }
        showActiveIfHasView();
    }

    public void deactivate() {
        active = false;
        showInactiveIfHasView();
    }

    public boolean isActive() {
        return active;
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
        backgroundView.setFill(Color.BISQUE);
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
