package si.gomoku.components;

import javafx.scene.Group;
import javafx.scene.Node;
import si.gomoku.players.Human;
import si.gomoku.players.Player;
import si.gomoku.rules.*;

import java.util.*;

/**
 * @author Tomasz Urbas
 */
public class Game extends Thread {

    private boolean active;

    private Board board;
    private Player darkPlayer;
    private Player lightPlayer;
    private Player currentPlayer;
    private int movesCounter = 0;

    private Map<Integer, Rule> ruleByMove;

    public Game() {
        board = new Board();
        darkPlayer = new Human(board, Stone.DARK);
        lightPlayer = new Human(board, Stone.LIGHT);
        initRules();
    }

    private void initRules() {
        ruleByMove = new HashMap<>();
        ruleByMove.put(0, new ProMoveOne(board));
        ruleByMove.put(1, new ProMoveTwo(board));
        ruleByMove.put(2, new ProMoveThree(board));
        ruleByMove.put(3, new UnlockAllFields(board));
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
    }

    private void startRound() {
        Optional.ofNullable(ruleByMove.get(movesCounter))
                .ifPresent(Rule::perform);
        currentPlayer.doMove();
    }

    private void endRound() {
        movesCounter++;
        if (isWinning() || isDraw()) {
            endGame();
            return;
        }
        currentPlayer = getNextPlayer();
    }

    private void endGame() {
        active = false;
    }

    private Player getNextPlayer() {
        return currentPlayer == darkPlayer ? lightPlayer : darkPlayer;
    }

    private boolean isWinning() {
        Field lastMove = board.getLastMove();
        int row = lastMove.getRow();
        int column = lastMove.getColumn();
        Stone stone = lastMove.getStone();
        return getLongestSequence(getFieldsFromBottomLeft(row + 4, column - 4), stone) == 5
                || getLongestSequence(getFieldsFromLeft(row, column - 4), stone) == 5
                || getLongestSequence(getFieldsFromTopLeft(row - 4, column - 4), stone) == 5
                || getLongestSequence(getFieldsFromTop(row - 4, column), stone) == 5;
    }

    private List<Field> getFieldsFromBottomLeft(int firstRow, int firstColumn) {
        List<Field> fields = new LinkedList<>();
        for (int i = 0; i < 9; i++) {
            Field field = board.getField(firstRow - i, firstColumn + i);
            fields.add(field);
        }
        return fields;
    }

    private List<Field> getFieldsFromLeft(int row, int firstColumn) {
        List<Field> fields = new LinkedList<>();
        for (int i = 0; i < 9; i++) {
            Field field = board.getField(row, firstColumn + i);
            fields.add(field);
        }
        return fields;
    }

    private List<Field> getFieldsFromTopLeft(int firstRow, int firstColumn) {
        List<Field> fields = new LinkedList<>();
        for (int i = 0; i < 9; i++) {
            Field field = board.getField(firstRow + i, firstColumn + i);
            fields.add(field);
        }
        return fields;
    }

    private List<Field> getFieldsFromTop(int firstRow, int column) {
        List<Field> fields = new LinkedList<>();
        for (int i = 0; i < 9; i++) {
            Field field = board.getField(firstRow + i, column);
            fields.add(field);
        }
        return fields;
    }

    private int getLongestSequence(List<Field> fields, Stone stone) {
        int longestSequence = 0;
        int currentSequence = 0;
        for (Field field : fields) {
            if (field.getStone() == stone) {
                currentSequence++;
            } else {
                if (currentSequence > longestSequence) {
                    longestSequence = currentSequence;
                }
                currentSequence = 0;
            }
        }
        return longestSequence;
    }

    private boolean isDraw() {
        return !board.isAnyEmptyField();
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
