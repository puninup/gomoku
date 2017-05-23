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

    private double seconds = 0;
    private long numberOfMoves = 0;

    Player(Board board, Stone stone) {
        this.board = board;
        this.stone = stone;
    }

    public void doMove(int moveNumber) {
        stopped = false;
        endOfTime = false;

        long startMove = System.nanoTime();
        move(moveNumber);
        long endMove = System.nanoTime();
        seconds += (endMove - startMove) / 1000000000.0;
        numberOfMoves++;
    }

    public abstract void move(int moveNumber);

    public void stop() {
        stopped = true;
    }

    public void nextTurn() {
        endOfTime = true;
        stop();
    }

    public double getAvgTime() {
        return seconds / numberOfMoves;
    }

    public void resetTime() {
        this.seconds = 0;
        this.numberOfMoves = 0;
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
