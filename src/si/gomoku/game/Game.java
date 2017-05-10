package si.gomoku.game;

import javafx.scene.Group;
import javafx.scene.Node;
import si.gomoku.players.Human;
import si.gomoku.players.Player;
import si.gomoku.rules.*;

/**
 * @author Tomasz Urbas
 */
public class Game extends Thread {

    private boolean active;

    private Board board;
    private RulesSet rules;
    private Player darkPlayer;
    private Player lightPlayer;
    private Player currentPlayer;
    private int movesCounter;



    public Game() {
        board = new Board();
        rules = new ProRules(board);
        darkPlayer = new Human(board, Stone.DARK);
        lightPlayer = new Human(board, Stone.LIGHT);
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            if (active) {
                startRound();
                endRound();
            }
            waitAWhile();
        }
    }

    public void startGame() {
        currentPlayer = darkPlayer;
        active = true;
        movesCounter = 0;
    }

    private void startRound() {
        rules.performForMove(movesCounter + 1);
        currentPlayer.doMove();
    }

    private void resetGame() {
        board.reset();
        startGame();
    }

    private void endRound() {
        movesCounter++;
        if (rules.isWinning() || rules.isDraw()) {
            endGame();
            return;
        }
        currentPlayer = getNextPlayer();
    }

    private void endGame() {
        active = false;
        resetGame();
    }

    private Player getNextPlayer() {
        return currentPlayer == darkPlayer ? lightPlayer : darkPlayer;
    }

    private synchronized void waitAWhile() {
        try {
            wait(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // VIEW ------------------------------
    private Group view;

    public void setUpView() {
        board.setUpView();
        view = new Group(board.getView());
    }

    public Node getView() {
        return view;
    }
}
