package si.gomoku.actions;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import si.gomoku.game.Game;

/**
 * @author Tomasz Urbas
 */
public class StopGame implements EventHandler<MouseEvent> {

    private Game game;

    public StopGame(Game game) {
        this.game = game;
    }

    @Override
    public void handle(MouseEvent event) {
        game.stopGame();
    }
}
