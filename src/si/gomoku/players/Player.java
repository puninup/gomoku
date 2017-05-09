package si.gomoku.players;

import si.gomoku.components.Board;
import si.gomoku.components.Stone;

/**
 * @author Tomasz Urbas
 */
public abstract class Player {

    Board board;
    Stone stone;

    Player(Board board, Stone stone) {
        this.board = board;
        this.stone = stone;
    }

    public abstract void doMove();
}
