package si.gomoku.players;

import si.gomoku.game.Board;
import si.gomoku.game.Stone;

/**
 * @author Tomasz Urbas
 */
public abstract class Player {

    boolean stopped;
    Board board;
    Stone stone;

    Player(Board board, Stone stone) {
        this.board = board;
        this.stone = stone;
    }

    public void doMove() {
        move();
        stopped = false;
    }

    public abstract void move();

    public void stop() {
        stopped = true;
    }

    public enum Type {
        HUMAN {
            @Override
            public Player getInstance(Board board, Stone stone) {
                return new Human(board, stone);
            }

            @Override
            public String toString() {
                return "Człowiek";
            }
        };

        public abstract Player getInstance(Board board, Stone stone);
    }
}
