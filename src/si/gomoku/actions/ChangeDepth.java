package si.gomoku.actions;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import si.gomoku.players.Player;

/**
 * @author Tomasz Urbas
 */
public class ChangeDepth implements ChangeListener<Number> {

    private Player player;

    public ChangeDepth(Player player) {
        this.player = player;
    }


    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        player.setDepth(newValue.intValue());
    }
}
