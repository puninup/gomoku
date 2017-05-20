package si.gomoku.heuristics;

import si.gomoku.evaluators.Evaluator;
import si.gomoku.game.Board;
import si.gomoku.game.Stone;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tomasz Urbas
 */
public abstract class Heuristic {
    private Map<Evaluator, Integer> weightOfEvaluators = new HashMap<>();
    private int totalWeight = 0;

    void addEvaluator(Evaluator evaluator) {
        addEvaluatorWithWeight(evaluator, 1);
    }

    void addEvaluatorWithWeight(Evaluator evaluator, int weight) {
        weightOfEvaluators.put(evaluator, weight);
        totalWeight += weight;
    }

    public int evaluate() {
        int value = 0;
        for (Map.Entry<Evaluator, Integer> weightOfEvaluator : weightOfEvaluators.entrySet())
        {
            Evaluator evaluator = weightOfEvaluator.getKey();
            int weight = weightOfEvaluator.getValue();
            value += evaluator.evaluate() * weight / totalWeight;
        }
        return value;
    }

    public void renewWith(Board board, Stone stone) {
        for(Evaluator evaluator : weightOfEvaluators.keySet()) {
            evaluator.renew(board, stone);
        }
    }

    public void updateValueFor(int row, int column) {
        for(Evaluator evaluator : weightOfEvaluators.keySet()) {
            evaluator.updateValueFor(row, column);
        }
    }

    public void revertUpdate() {
        for(Evaluator evaluator : weightOfEvaluators.keySet()) {
            evaluator.revertUpdate();
        }
    }
}
