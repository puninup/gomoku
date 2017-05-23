package si.gomoku;

import si.gomoku.game.Game;
import si.gomoku.game.GameObserver;
import si.gomoku.game.Stone;
import si.gomoku.heuristics.TestHeuristic;
import si.gomoku.players.Player;
import si.gomoku.players.PlayerAI;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tomasz Urbas
 */
public class Test implements GameObserver {
    private double group = 0.00;
    private double neighbors = 0.00;
    private double sequence = 0.00;

    private double bestGroup = 0.3;
    private double bestNeighbors = 0.4;
    private double bestSequence = 0.00;

    private boolean revenge = false;
    private List<String> possible = new LinkedList<>();

    private Game game;
    private PlayerAI currentBest;
    private PlayerAI currentTest;

    public static void main(String[] args) {
        Test test = new Test();
        test.startTest();
    }

    public void startTest() {
        game = new Game();
        game.start();
        game.addObserver(this);

        currentBest = (PlayerAI) game.assignPlayer(Player.Type.ALPHA_BETA, Stone.DARK);
        currentTest = (PlayerAI) game.assignPlayer(Player.Type.ALPHA_BETA, Stone.LIGHT);

        prepareAndStartNextGame();
    }

    private void prepareAndStartNextGame() {
        if (revenge) {
            currentTest.setHeuristic(new TestHeuristic(bestGroup, bestNeighbors, bestSequence));
            currentBest.setHeuristic(new TestHeuristic(group, neighbors, sequence));
        } else {
            if (group < 1) {
                group += 0.2;
            } else if (neighbors < 1) {
                group = 0;
                neighbors += 0.2;
            } else if (sequence < 1) {
                group = 0;
                neighbors = 0;
                sequence += 0.2;
            } else {
                System.out.println("KONIEC");
                System.out.println(possible);
                game.interrupt();
                return;
            }

            currentBest.setHeuristic(new TestHeuristic(bestGroup, bestNeighbors, bestSequence));
            currentTest.setHeuristic(new TestHeuristic(group, neighbors, sequence));
        }
        game.resetGame();
        game.startGame();
    }

    @Override
    public void nextTurn(Stone moving) {
    }

    @Override
    public void endGame(Stone winner) {
        System.out.println(String.format("(%.2f %.2f %.2f) vs (%.2f %.2f %.2f) : %s %s",
                bestGroup, bestNeighbors, bestSequence, group, neighbors, sequence, winner, (revenge) ? "REVENGE" : ""));

        if ((winner == Stone.DARK || winner == null) && revenge) {
            possible.add(String.format("%.2f %.2f %.2f", bestGroup, bestNeighbors, bestSequence));
            revenge = false;

            bestGroup = group;
            bestNeighbors = neighbors;
            bestSequence = sequence;
        } else if (winner == Stone.LIGHT && revenge) {
            revenge = false;
        } else if (winner == Stone.LIGHT) {
            revenge = true;
        }

        prepareAndStartNextGame();
    }

    @Override
    public void stopGame() {

    }
}
