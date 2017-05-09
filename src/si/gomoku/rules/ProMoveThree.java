package si.gomoku.rules;

import si.gomoku.components.Board;

/**
 * Only fields at least 3 squares from middle are active
 * @author Tomasz Urbas
 */
public class ProMoveThree implements Rule {

    private Board board;
    private int middleRow;
    private int middleColumn;

    public ProMoveThree(Board board) {
        this.board = board;
        this.middleRow = Board.FIELDS_IN_ROW / 2;
        this.middleColumn = Board.FIELDS_IN_ROW / 2;
    }

    @Override
    public void perform() {
        for (int row = 0; row < Board.FIELDS_IN_ROW; row++) {
            for (int column = 0; column < Board.FIELDS_IN_ROW; column++) {
                board.activateField(row, column);
                deactivateIfMiddleSquare(row, column);
            }
        }
    }

    private void deactivateIfMiddleSquare(int row, int column) {
        int spaceFromMiddle = 3;
        if (row > middleRow - spaceFromMiddle && row < middleRow + spaceFromMiddle
                && column > middleColumn - spaceFromMiddle && column < middleColumn + spaceFromMiddle) {
            board.deactivateField(row, column);
        }
    }
}
