package si.gomoku.control;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import si.gomoku.actions.StartNewGame;
import si.gomoku.actions.StopGame;
import si.gomoku.game.Game;

/**
 * @author Tomasz Urbas
 */
public class GameControl {

    private Game game;

    public GameControl(Game game) {
        this.game = game;
    }

    // VIEW ----------------------
    private HBox view;

    private void setUpView() {
        Button newGame = new Button("New game");
        newGame.setOnMouseClicked(new StartNewGame(game));

        Button stopGame = new Button("Stop game");
        stopGame.setOnMouseClicked(new StopGame(game));

        Pane space = new Pane();
        HBox.setHgrow(space, Priority.ALWAYS);

        Label timerLabel = new Label("CountDownTimer");
        timerLabel.textProperty().bind(game.getTimerStringTime());
        timerLabel.setFont(Font.font("Cambria", 23));

        view = new HBox(newGame, stopGame, space, timerLabel);
        view.setPadding(new Insets(0, 0, 10, 0));
        view.setSpacing(10);
    }

    public HBox setUpAndGetView() {
        setUpView();
        return view;
    }
}
