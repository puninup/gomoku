package si.gomoku.actions;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import si.gomoku.game.Game;

/**
 * @author Tomasz Urbas
 */
public class StartNewGame implements EventHandler<MouseEvent> {

    private Game game;

    public StartNewGame(Game game) {
        this.game = game;
    }

    @Override
    public void handle(MouseEvent event) {
        game.resetGame();
        game.startGame();
    }
}
