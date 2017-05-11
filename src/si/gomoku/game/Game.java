package si.gomoku.game;

import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import si.gomoku.CountDownTimer;
import si.gomoku.players.Human;
import si.gomoku.players.Player;
import si.gomoku.game.rules.*;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

/**
 * @author Tomasz Urbas
 */
public class Game extends Thread implements Observer {

    private boolean active;
    private boolean reset;

    private Board board;
    private RulesSet rules;
    private Player darkPlayer;
    private Player lightPlayer;
    private Player currentPlayer;
    private CountDownTimer timer;
    private int movesCounter;

    public Game() {
        board = new Board();
        rules = new ProRules(board);
        darkPlayer = new Human(board, Stone.DARK);
        lightPlayer = new Human(board, Stone.LIGHT);
        timer = new CountDownTimer();
        timer.setUp(1,0);
        timer.addObserver(this);
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            reset = false;
            if (active) {
                startRound();
            }
            if (active && !reset) {
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
        timer.reset();
        timer.start();
        currentPlayer.doMove();
    }

    private void endRound() {
        timer.stop();
        movesCounter++;
        if (rules.isWinning() || rules.isDraw()) {
            endGame();
            return;
        }
        currentPlayer = getNextPlayer();
    }

    private void endGame() {
        stopGame();
    }

    public void resetGame() {
        this.reset = true;
        stopGame();
        board.reset();
    }

    public void stopGame() {
        this.active = false;
        timer.stop();
        Optional.ofNullable(currentPlayer).ifPresent(Player::stop);
        currentPlayer = null;
    }

    private Player getNextPlayer() {
        return currentPlayer == darkPlayer ? lightPlayer : darkPlayer;
    }

    public StringProperty getTimerStringTime() {
        return timer.getStringTime();
    }

    private synchronized void waitAWhile() {
        try {
            wait(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        currentPlayer.stop();
    }

    // VIEW ------------------------------
    private Group view;

    private void setUpView() {
        board.setUpView();
        view = new Group(board.getView());
    }

    public Node setUpAndGetView() {
        setUpView();
        return view;
    }
}
