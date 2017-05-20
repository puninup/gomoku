package si.gomoku.players;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import si.gomoku.actions.ChangeDepth;
import si.gomoku.game.Board;
import si.gomoku.game.Field;
import si.gomoku.game.Stone;
import si.gomoku.game.rules.RulesSet;
import si.gomoku.heuristics.FieldsNearby;
import si.gomoku.heuristics.GroupHeuristic;
import si.gomoku.heuristics.Heuristic;
import si.gomoku.heuristics.QualityHeuristic;

import java.util.List;

/**
 * @author Tomasz Urbas
 */
public class AlphaBeta extends Player {

    private RulesSet rules;
    private int maxDepth = 2;
    private Heuristic heuristic = new GroupHeuristic();
    private QualityHeuristic qualityHeuristic = new FieldsNearby();
    private Field bestMove;

    AlphaBeta(Board board, Stone stone, RulesSet rules) {
        super(board, stone);
        this.rules = rules;
    }

    @Override
    public void move(int moveNumber) {
        bestMove = Field.EMPTY_FIELD;
        Board board = this.board.copy();
        heuristic.renewWith(board, stone);
        alphaBeta(Level.MAX, 1, Integer.MAX_VALUE, moveNumber, board);

        if (stopped && !endOfTime) {
            return;
        }

        this.board.putStone(bestMove.getRow(), bestMove.getColumn(), stone);
    }

    private int alphaBeta(Level level, int depth, int currentBest, int moveNumber, Board board) {
        if (stopped) {
            return Integer.MIN_VALUE;
        }
        if (isLeaf(depth, board)) {
            return heuristic.evaluate();
        }

        int best = level.getWorstValue();
        rules.performForMove(moveNumber, board);
        List<Field> fields = qualityHeuristic.getPreferableFields(board);
        for (Field field : fields) {
            board.putStone(field.getRow(), field.getColumn(), level.getStone(stone));
            heuristic.updateValueFor(field.getRow(), field.getColumn());
            int current = alphaBeta(level.opposite(), depth + 1, best, moveNumber + 1, board);
            board.pickUpStone(field.getRow(), field.getColumn());
            heuristic.updateValueFor(field.getRow(), field.getColumn());
            if (level.isBetter(current, best)) {
                best = current;
                if (depth == 1) {
                    bestMove = field;
                }
                if (level.isBetter(best, currentBest)) {
                    return best;
                }
            }
        }
        return best;
    }

    private boolean isLeaf(int depth, Board board) {
        return depth > maxDepth
                || rules.isWinning(board)
                || rules.isDraw(board);
    }

    @Override
    public void setDepth(int depth) {
        maxDepth = depth;
    }

    // VIEW ----------------------------
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
        deepSlider.valueProperty().addListener(new ChangeDepth(this));
        deepSlider.setValue(2);

        view.getChildren().addAll(deepLabel, new HBox(deepSlider, deepValueLabel));
        view.setPadding(new Insets(10));
    }

    @Override
    public VBox getSettingsView() {
        if (view == null) {
            setUpView();
        }
        return view;
    }
}
