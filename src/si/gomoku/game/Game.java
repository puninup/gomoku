package si.gomoku.game;

import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import si.gomoku.CountDownTimer;
import si.gomoku.game.rules.ProRules;
import si.gomoku.game.rules.RulesSet;
import si.gomoku.players.Player;

import java.util.*;

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

    private List<GameObserver> observers = new LinkedList<>();

    public Game() {
        board = new Board();
        board.setUpFields();
        rules = new ProRules();
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
        rules.performForMove(movesCounter + 1, board);

        for (GameObserver observer : observers) {
            observer.nextTurn(board.getLastMove().getStone().oppositeStone());
        }

        timer.reset();
        timer.start();
        currentPlayer.doMove(movesCounter + 1);
    }

    private void endRound() {
        timer.stop();
        movesCounter++;
        if (rules.isWinning(board) || rules.isDraw(board)) {
            endGame();
            return;
        }
        currentPlayer = getNextPlayer();
    }

    private void endGame() {
        stopGame();

        Stone winner = null;
        if (!rules.isDraw(board)) {
            winner = board.getLastMove().getStone();
        }

        for (GameObserver observer : observers) {
            observer.endGame(winner);
        }
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

    public void assignPlayer(Player.Type playerType, Stone stone) {
        Player player = playerType.getInstance(board, stone, rules);
        switch (stone) {
            case DARK:
                this.darkPlayer = player;
                break;
            case LIGHT:
                this.lightPlayer = player;
        }
    }

    public Player getPlayer(Stone stone) {
        switch (stone) {
            case DARK:
                return darkPlayer;
            default:
                return lightPlayer;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        currentPlayer.nextTurn();
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
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
