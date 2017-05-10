package si.gomoku;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import si.gomoku.game.Game;

/**
 * @author Tomasz Urbas
 */
public class Gomoku extends Application {

    private Game game;
    private GameControl gameControl;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        game = new Game();
        game.start();
        primaryStage.setTitle("TU GomokuPRO");
        primaryStage.setScene(getScene());
        primaryStage.show();
        game.startGame();
    }

    private Scene getScene() {
        game.setUpView();
        BorderPane root = new BorderPane();
        root.setCenter(game.getView());
        return new Scene(root, 1000, 650);
    }

    @Override
    public void stop() throws Exception {
        game.interrupt();
    }
}
