package si.gomoku.players;

import javafx.scene.layout.VBox;
import si.gomoku.game.Board;
import si.gomoku.game.Field;
import si.gomoku.game.Stone;

/**
 * @author Tomasz Urbas
 */
public class Human extends Player {

    public Human(Board board, Stone stone) {
        super(board, stone);
    }

    @Override
    public void move(int moveNumber) {
        board.enableHumanInteraction(stone);
        waitForMove();
        board.disableHumanInteraction();
    }

    private void waitForMove() {
        Field lastMove = board.getLastMove();
        while (lastMove.equals(board.getLastMove()) && !stopped) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    @Override
    public void setDepth(int value) {
    }

    // VIEW --------------------------
    public VBox getSettingsView() {
        return new VBox();
    }
}
