package si.gomoku;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import si.gomoku.components.Game;

/**
 * @author Tomasz Urbas
 */
public class Gomoku extends Application {

    private Game game;

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
        Group root = new Group(game.getView());
        return new Scene(root, 800, 600);
    }

    @Override
    public void stop() throws Exception {
        game.interrupt();
    }
}
