package si.gomoku.actions;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import si.gomoku.game.Game;

/**
 * @author Tomasz Urbas
 */
public class SetUpTimer implements ChangeListener<Boolean> {

    private Game game;
    private TextField textField;
    private String startValue;

    public SetUpTimer(Game game, TextField textField) {
        this.game = game;
        this.textField = textField;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            startValue = textField.getText();
            return;
        }

        String time = textField.getText();
        int minutes;
        int seconds;
        if (time.matches("\\d*")) {
            int tempSeconds = Integer.valueOf(time);
            minutes = tempSeconds / 60;
            seconds = tempSeconds % 60;
        } else if (time.matches("\\d*:\\d\\d*")) {
            int dividerIndex = time.indexOf(":");
            minutes = Integer.valueOf(time.substring(0, dividerIndex));
            seconds = Integer.valueOf(time.substring(dividerIndex + 1));
        } else {
            textField.setText(startValue);
            return;
        }
        game.setUpTimer(minutes, seconds);
        textField.setText(String.format("%d:%02d", minutes, seconds));
    }
}
