package si.gomoku.rules;

/**
 * @author Tomasz Urbas
 */
public interface RulesSet {
    void performForMove(int move);
    boolean isWinning();
    boolean isDraw();
}
