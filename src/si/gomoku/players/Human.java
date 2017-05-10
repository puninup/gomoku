package si.gomoku.players;

import si.gomoku.game.Board;
import si.gomoku.game.Field;
import si.gomoku.game.Stone;

/**
 * @author Tomasz Urbas
 */
public class Human extends Player {

    public Human(Board board, Stone stone) {
        super(board, stone);
    }

    @Override
    public void doMove() {
        board.enableHumanInteraction(stone);
        waitForMove();
        board.disableHumanInteraction();
    }

    private void waitForMove() {
        Field lastMove = board.getLastMove();
        while (lastMove.equals(board.getLastMove())) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
