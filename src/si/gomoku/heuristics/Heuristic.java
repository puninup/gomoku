package si.gomoku.heuristics;

import si.gomoku.game.Board;
import si.gomoku.game.Stone;
import si.gomoku.heuristics.evaluators.Evaluator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tomasz Urbas
 */
public abstract class Heuristic {
    private Map<Evaluator, Double> weightOfEvaluators = new HashMap<>();

    void addEvaluator(Evaluator evaluator) {
        addEvaluatorWithWeight(evaluator, 1);
    }

    void addEvaluatorWithWeight(Evaluator evaluator, double weight) {
        weightOfEvaluators.put(evaluator, weight);
    }

    public int evaluate() {
        int value = 0;
        for (Map.Entry<Evaluator, Double> weightOfEvaluator : weightOfEvaluators.entrySet())
        {
            Evaluator evaluator = weightOfEvaluator.getKey();
            double weight = weightOfEvaluator.getValue();
            value += evaluator.evaluate() * weight;
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

    public enum Type {
        GROUP {
            @Override
            public Heuristic getInstance() {
                return new GroupHeuristic();
            }

            @Override
            public String toString() {
                return "Grupy";
            }
        },
        SEQUENCE {
            @Override
            public Heuristic getInstance() {
                return new SequenceHeuristic();
            }

            @Override
            public String toString() {
                return "Sekwencje";
            }
        },
        SEQUENCE_GROUP {
            @Override
            public Heuristic getInstance() {
                return new SequenceGroupHeuristic();
            }

            @Override
            public String toString() {
                return "Sekwencje + grupy";
            }
        },
        GROUP_NEIGHBORS_BALANCED {
            @Override
            public Heuristic getInstance() {
                return new SequenceNeighborsHeuristic();
            }

            @Override
            public String toString() {
                return "Grupy + sąsiedzi (zrównoważony)";
            }
        },
        GROUP_NEIGHBORS {
            @Override
            public Heuristic getInstance() {
                return new GroupNeighborsHeuristic();
            }

            @Override
            public String toString() {
                return "Grupy + sąsiedzi (grupa przeważa)";
            }
        },
        GROUP_NEIGHBORS_SEQUENCE {
            @Override
            public Heuristic getInstance() {
                return new GroupNeighborsSequenceHeuristic();
            }

            @Override
            public String toString() {
                return "Grupy + sąsiedzi + sekwencje (zrównoważony)";
            }
        };

        public abstract Heuristic getInstance();
    }
}
