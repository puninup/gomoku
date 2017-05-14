package si.gomoku.control;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import si.gomoku.actions.ChangeSiDeep;
import si.gomoku.game.Game;
import si.gomoku.game.Stone;

/**
 * @author Tomasz Urbas
 */
public class SISettings {

    private Game game;
    private Stone stone;

    public SISettings(Game game, Stone stone) {
        this.game = game;
        this.stone = stone;
    }

    // VIEW ------------------------
    private VBox view;

    private void setUpView() {
        view = new VBox();

        Label deepLabel = new Label("Głębokość");

        Label deepValueLabel = new Label();
        deepValueLabel.setPadding(new Insets(2,0,0,5));

        Slider deepSlider = new Slider();
        deepSlider.setMin(1);
        deepSlider.setMax(5);
        deepSlider.setPadding(new Insets(5,0,0,0));
        deepSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            Long value = Math.round((double) newVal);
            deepSlider.setValue(value);
            deepValueLabel.setText(value.toString());
        });
        deepSlider.valueProperty().addListener(new ChangeSiDeep(game, stone));
        deepSlider.setValue(2);

        view.getChildren().addAll(deepLabel, new HBox(deepSlider, deepValueLabel));
    }

    public VBox setUpAndGetView() {
        setUpView();
        return view;
    }
}
