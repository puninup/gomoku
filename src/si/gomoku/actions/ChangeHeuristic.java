package si.gomoku.actions;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import si.gomoku.heuristics.Heuristic;
import si.gomoku.players.PlayerAI;

/**
 * @author Tomasz Urbas
 */
public class ChangeHeuristic implements ChangeListener<Heuristic.Type> {

    private PlayerAI player;

    public ChangeHeuristic(PlayerAI player) {
        this.player = player;
    }

    @Override
    public void changed(ObservableValue<? extends Heuristic.Type> observable, Heuristic.Type oldHeuristic, Heuristic.Type newHeuristic) {
        player.setHeuristic(newHeuristic);
    }
}
