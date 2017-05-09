package si.gomoku.rules;

import si.gomoku.components.Board;

/**
 * Only fields next to the middle are active
 * @author Tomasz Urbas
 */
public class ProMoveTwo implements Rule {

    private Board board;
    private int middleRow;
    private int middleColumn;

    public ProMoveTwo(Board board) {
        this.board = board;
        this.middleRow = Board.FIELDS_IN_ROW / 2;
        this.middleColumn = Board.FIELDS_IN_ROW / 2;
    }

    @Override
    public void perform() {
        for (int row = middleRow - 1; row <= middleRow + 1; row++) {
            for (int column = middleColumn - 1; column <= middleColumn + 1; column++) {
                board.activateField(row, column);
                deactivateIfMiddle(row, column);
            }
        }
    }

    private void deactivateIfMiddle(int row, int column) {
        if (row == middleRow && column == middleColumn) {
            board.deactivateField(row, column);
        }
    }
}
