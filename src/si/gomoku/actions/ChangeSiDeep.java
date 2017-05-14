package si.gomoku.actions;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import si.gomoku.game.Game;
import si.gomoku.game.Stone;

/**
 * @author Tomasz Urbas
 */
public class ChangeSiDeep implements ChangeListener<Number> {

    private Game game;
    private Stone stone;

    public ChangeSiDeep(Game game, Stone stone) {
        this.game = game;
        this.stone = stone;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        game.setPlayerDeep(newValue.intValue(), stone);
    }
}
