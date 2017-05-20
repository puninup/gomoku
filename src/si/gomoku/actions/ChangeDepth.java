package si.gomoku.actions;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import si.gomoku.players.PlayerAI;

/**
 * @author Tomasz Urbas
 */
public class ChangeDepth implements ChangeListener<Number> {

    private PlayerAI player;

    public ChangeDepth(PlayerAI player) {
        this.player = player;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        player.setDepth(newValue.intValue());
    }
}
