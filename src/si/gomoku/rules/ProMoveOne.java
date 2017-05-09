package si.gomoku.rules;

import si.gomoku.components.Board;

/**
 * Only middle field is active
 * @author Tomasz Urbas
 */
public class ProMoveOne implements Rule {

    private Board board;
    private int middleRow;
    private int middleColumn;

    public ProMoveOne(Board board) {
        this.board = board;
        this.middleRow = Board.FIELDS_IN_ROW / 2;
        this.middleColumn = Board.FIELDS_IN_ROW / 2;
    }

    @Override
    public void perform() {
        board.activateField(middleRow, middleColumn);
    }
}
