package si.gomoku.players;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import si.gomoku.actions.AddRemoveQualityHeuristic;
import si.gomoku.actions.ChangeDepth;
import si.gomoku.actions.ChangeHeuristic;
import si.gomoku.game.Board;
import si.gomoku.game.Stone;
import si.gomoku.game.rules.RulesSet;
import si.gomoku.heuristics.GroupHeuristic;
import si.gomoku.heuristics.Heuristic;
import si.gomoku.qualifiers.*;

/**
 * @author Tomasz Urbas
 */
public abstract class PlayerAI extends Player {

    RulesSet rules;
    private int maxDepth = 2;
    Heuristic heuristic = new GroupHeuristic();
    Qualifier qualifier = new Qualifier();

    PlayerAI(Board board, Stone stone, RulesSet rules) {
        super(board, stone);
        this.rules = rules;
    }

    public void setDepth(int depth) {
        maxDepth = depth;
    }

    public void setHeuristic(Heuristic.Type heuristicType) {
        this.heuristic = heuristicType.getInstance();
    }

    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public void addQualityHeuristic(QualityHeuristic heuristic) {
        this.qualifier.addHeuristic(heuristic);
    }

    public void removeQualityHeuristic(QualityHeuristic heuristic) {
        this.qualifier.removeHeuristic(heuristic);
    }

    boolean isLeaf(int depth, Board board) {
        return depth == maxDepth
                || rules.isWinning(board)
                || rules.isDraw(board);
    }

    // VIEW ----------------------------
    private VBox view;

    private void setUpView() {
        view = new VBox();

        Label deepLabel = new Label("Głębokość");

        Label deepValueLabel = new Label();
        deepValueLabel.setPadding(new Insets(-2,0,0,10));

        Slider deepSlider = new Slider();
        deepSlider.setMin(1);
        deepSlider.setMax(5);
        deepSlider.setPadding(new Insets(7,0,0,0));
        deepSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            Long value = Math.round((double) newVal);
            deepSlider.setValue(value);
            deepValueLabel.setText(value.toString());
        });
        deepSlider.valueProperty().addListener(new ChangeDepth(this));
        deepSlider.setValue(2);

        Label heuristicLabel = new Label("Heurystyka");
        deepValueLabel.setPadding(new Insets(5,0,0,0));

        ChoiceBox<Heuristic.Type> heuristicTypes = new ChoiceBox<>(FXCollections.observableArrayList(Heuristic.Type.values()));
        heuristicTypes.getSelectionModel().selectedItemProperty().addListener(new ChangeHeuristic(this));
        heuristicTypes.setPrefWidth(180);

        Label moveLabel = new Label("Zakres ruchu");
        moveLabel.setPadding(new Insets(5,0,0,0));

        ToggleGroup moveZoneGroup = new ToggleGroup();

        ToggleButton neighborsFields = new ToggleButton("Sąsiedzi");
        neighborsFields.selectedProperty()
                .addListener(new AddRemoveQualityHeuristic(this, new NeighborsFields()));
        neighborsFields.setToggleGroup(moveZoneGroup);

        ToggleButton neighborsOfNeighborsFields = new ToggleButton("Sąsiedzi sąsiadów");
        neighborsOfNeighborsFields.selectedProperty()
                .addListener(new AddRemoveQualityHeuristic(this, new NeighborsOfNeighborsFields()));
        neighborsOfNeighborsFields.setToggleGroup(moveZoneGroup);

        Label sortingLabel = new Label("Wybór kolejności węzłów");
        sortingLabel.setPadding(new Insets(5,0,0,0));

        HBox moveZone = new HBox(neighborsFields, neighborsOfNeighborsFields);

        ToggleGroup sortingGroup = new ToggleGroup();

        ToggleButton lastMoveTracker = new ToggleButton("Najbliżej ostatniego ruchu");
        lastMoveTracker.selectedProperty()
                .addListener(new AddRemoveQualityHeuristic(this, new LastMoveTracker()));
        lastMoveTracker.setToggleGroup(sortingGroup);

        ToggleButton sequencesTracker = new ToggleButton("Wokoło największych ciągów");
        sequencesTracker.selectedProperty()
                .addListener(new AddRemoveQualityHeuristic(this, new SequencesTracker()));
        sequencesTracker.setToggleGroup(sortingGroup);

        VBox sortingZone = new VBox(lastMoveTracker, sequencesTracker);

        view.getChildren().addAll(deepLabel, new HBox(deepSlider, deepValueLabel),
                heuristicLabel, heuristicTypes, moveLabel, moveZone, sortingLabel, sortingZone);
        view.setPadding(new Insets(10));
        heuristicTypes.setValue(Heuristic.Type.GROUP);
    }

    @Override
    public VBox getSettingsView() {
        if (view == null) {
            setUpView();
        }
        return view;
    }
}
