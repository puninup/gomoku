package si.gomoku;

/**
 * @author Tomasz Urbas
 */
public enum Direction {
    TOP(-1, 0),
    TOP_RIGHT(-1, 1),
    RIGHT(0, 1),
    BOTTOM_RIGHT(1, 1),
    BOTTOM(1, 0),
    BOTTOM_LEFT(1, -1),
    LEFT(0, -1),
    TOP_LEFT(-1, -1);

    int rowStep;
    int columnStep;

    Direction(int rowStep, int columnStep) {
        this.rowStep = rowStep;
        this.columnStep = columnStep;
    }

    public int getRowStep() {
        return rowStep;
    }

    public int getColumnStep() {
        return columnStep;
    }
}
