package si.gomoku.actions;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import si.gomoku.game.Game;
import si.gomoku.game.Stone;
import si.gomoku.players.Player;

/**
 * @author Tomasz Urbas
 */
public class ChangePlayer implements ChangeListener<Player.Type> {

    private Game game;
    private Stone stone;

    public ChangePlayer(Game game, Stone stone) {
        this.game = game;
        this.stone = stone;
    }

    @Override
    public void changed(ObservableValue<? extends Player.Type> observable, Player.Type oldPlayer, Player.Type newPlayer) {
        game.assignPlayer(newPlayer, stone);
    }
}
