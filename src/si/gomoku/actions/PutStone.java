package si.gomoku.actions;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import si.gomoku.game.Board;
import si.gomoku.game.Field;
import si.gomoku.game.Stone;

/**
 * @author Tomasz Urbas
 */
public class PutStone implements EventHandler<MouseEvent> {

    private Board board;
    private Field field;
    private Stone stone;

    public PutStone(Board board, Field field, Stone stone) {
        this.board = board;
        this.field = field;
        this.stone = stone;
    }

    @Override
    public void handle(MouseEvent event) {
        board.putStone(field.getRow(), field.getColumn(), stone);
    }
}
