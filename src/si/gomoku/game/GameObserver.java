package si.gomoku.game;

/**
 * @author Tomasz Urbas
 */
public interface GameObserver {
    void nextTurn(Stone moving);
    void endGame(Stone winner);
    void stopGame();
}
