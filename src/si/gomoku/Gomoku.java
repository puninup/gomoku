package si.gomoku;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import si.gomoku.control.PlayersSettings;
import si.gomoku.game.Game;
import si.gomoku.control.GameControl;

/**
 * @author Tomasz Urbas
 */
public class Gomoku extends Application {

    private Game game;
    private GameControl gameControl;
    private PlayersSettings playersSettings;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        game = new Game();
        gameControl = new GameControl(game);
        playersSettings = new PlayersSettings(game);
        game.start();

        primaryStage.setTitle("TU GomokuPRO");
        primaryStage.setScene(getScene());
        primaryStage.show();
    }

    private Scene getScene() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15, 15, 15, 15));
        root.setLeft(game.setUpAndGetView());
        root.setTop(gameControl.setUpAndGetView());
        root.setRight(playersSettings.setUpAndGetView());
        return new Scene(root, 1000, 650);
    }

    @Override
    public void stop() throws Exception {
        game.interrupt();
    }
}
