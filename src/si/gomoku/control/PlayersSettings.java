package si.gomoku.control;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private VBox view;
    private VBox darkPlayerBox;
    private VBox lightPlayerBox;

    private void setUpView() {
        createDarkPlayerBox();
        createLightPlayerBox();

        view = new VBox(darkPlayerBox, lightPlayerBox);
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
        playerBox.setPrefSize(200, 130);
        playerBox.setMaxSize(200, 130);
        playerBox.setAlignment(Pos.TOP_CENTER);

        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Cambria", 16));
        nameLabel.setPadding(new Insets(10));

        ChoiceBox<Player.Type> playerTypes = new ChoiceBox<>(FXCollections.observableArrayList(Player.Type.values()));
        playerTypes.getSelectionModel().selectedItemProperty().addListener(new ChangePlayer(game, stone));
        playerTypes.setValue(Player.Type.HUMAN);
        playerTypes.setPrefWidth(180);

        VBox siSettings = new SISettings(game, stone).setUpAndGetView();
        siSettings.setPadding(new Insets(10));

        playerBox.getChildren().addAll(nameLabel, playerTypes, siSettings);
    }

    public VBox setUpAndGetView() {
        setUpView();
        return view;
    }
}
