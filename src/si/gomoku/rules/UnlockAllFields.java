package si.gomoku.rules;

import si.gomoku.components.Board;

/**
 * @author Tomasz Urbas
 */
public class UnlockAllFields implements Rule {

    private Board board;

    public UnlockAllFields(Board board) {
        this.board = board;
    }

    @Override
    public void perform() {
        for (int row = 0; row < Board.FIELDS_IN_ROW; row++) {
            for (int column = 0; column < Board.FIELDS_IN_ROW; column++) {
                board.activateField(row, column);
            }
        }
    }
}
