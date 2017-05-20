package si.gomoku.actions;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import si.gomoku.players.PlayerAI;
import si.gomoku.qualifiers.QualityHeuristic;

/**
 * @author Tomasz Urbas
 */
public class AddRemoveQualityHeuristic implements ChangeListener<Boolean> {

    private PlayerAI player;
    private QualityHeuristic heuristic;

    public AddRemoveQualityHeuristic(PlayerAI player, QualityHeuristic heuristic) {
        this.player = player;
        this.heuristic = heuristic;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue.equals(true)) {
            player.addQualityHeuristic(heuristic);
        } else {
            player.removeQualityHeuristic(heuristic);
        }
    }
}
