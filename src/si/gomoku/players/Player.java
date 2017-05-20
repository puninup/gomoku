package si.gomoku.players;

import javafx.scene.layout.VBox;
import si.gomoku.game.Board;
import si.gomoku.game.Stone;
import si.gomoku.game.rules.RulesSet;

/**
 * @author Tomasz Urbas
 */
public abstract class Player {
    boolean stopped;
    boolean endOfTime;
    Board board;
    Stone stone;

    Player(Board board, Stone stone) {
        this.board = board;
        this.stone = stone;
    }

    public void doMove(int moveNumber) {
        stopped = false;
        endOfTime = false;
        move(moveNumber);
    }

    public abstract void move(int moveNumber);
    public abstract void setDepth(int value);

    public void stop() {
        stopped = true;
    }

    public void nextTurn() {
        endOfTime = true;
        stop();
    }

    public abstract VBox getSettingsView();

    public enum Type {
        HUMAN {
            @Override
            public Player getInstance(Board board, Stone stone, RulesSet rulesSet) {
                return new Human(board, stone);
            }

            @Override
            public String toString() {
                return "Człowiek";
            }
        },
        MIN_MAX {
            @Override
            public Player getInstance(Board board, Stone stone, RulesSet rules) {
                return new MinMax(board, stone, rules);
            }

            @Override
            public String toString() {
                return "Min-max";
            }
        },
        ALPHA_BETA {
            @Override
            public Player getInstance(Board board, Stone stone, RulesSet rules) {
                return new AlphaBeta(board, stone, rules);
            }

            @Override
            public String toString() {
                return "Alfa-beta";
            }
        };

        public abstract Player getInstance(Board board, Stone stone, RulesSet rules);
    }
}
