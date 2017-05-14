package si.gomoku.control;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import si.gomoku.actions.ChangePlayer;
import si.gomoku.game.Game;
import si.gomoku.game.Stone;
import si.gomoku.players.Player;

/**
 * @author Tomasz Urbas
 */
public class PlayersSettings {

    private Game game;

    public PlayersSettings(Game game) {
        this.game = game;
    }

    // VIEW -----------------------
    private HBox view;
    private VBox darkPlayerBox;
    private VBox lightPlayerBox;

    private void setUpView() {
        createDarkPlayerBox();
        createLightPlayerBox();

        view = new HBox(darkPlayerBox, lightPlayerBox);
        view.setSpacing(15);
    }

    private void createDarkPlayerBox() {
        darkPlayerBox = new VBox();
        createPlayerBox(darkPlayerBox, Stone.DARK, "Gracz czarny");
    }

    private void createLightPlayerBox() {
        lightPlayerBox = new VBox();
        createPlayerBox(lightPlayerBox, Stone.LIGHT, "Gracz biały");
    }

    private void createPlayerBox(VBox playerBox, Stone stone, String name) {
        playerBox.setBorder(new Border(
                new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))
        ));
        playerBox.setPrefSize(170, 100);
        playerBox.setMaxSize(170, 100);
        playerBox.setAlignment(Pos.BASELINE_CENTER);

        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Cambria", 16));

        ChoiceBox<Player.Type> playerTypes = new ChoiceBox<>(FXCollections.observableArrayList(Player.Type.values()));
        playerTypes.getSelectionModel().selectedItemProperty().addListener(new ChangePlayer(game, stone));
        playerTypes.setValue(Player.Type.HUMAN);
        playerTypes.setPrefWidth(150);

        playerBox.getChildren().add(nameLabel);
        playerBox.getChildren().add(playerTypes);
    }

    public HBox setUpAndGetView() {
        setUpView();
        return view;
    }
}
