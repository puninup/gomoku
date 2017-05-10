package si.gomoku.players;

import si.gomoku.game.Board;
import si.gomoku.game.Stone;

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
