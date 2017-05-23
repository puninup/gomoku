package si.gomoku.qualifiers;

import si.gomoku.game.Board;
import si.gomoku.game.Field;
import si.gomoku.game.MatrixPartIndex;
import si.gomoku.game.Stone;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Tomasz Urbas
 */
public class SequencesTracker implements QualityHeuristic {

    private Map<Field, Integer> valuesOfFields = new HashMap<>();

    @Override
    public List<Field> qualify(List<Field> fields, Board board) {
        valuesOfFields.clear();
        evaluateLine(board);
        return valuesOfFields.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .map(Map.Entry::getKey)
                .filter(fields::contains)
                .collect(Collectors.toList());
    }

    private void evaluateLine(Board board) {
        List<MatrixPartIndex> partIndexes = board.getAllPartIndexes();
        for (MatrixPartIndex partIndex : partIndexes) {
            List<Field> fields = board.getFieldSequence(partIndex);
            evaluateLine(fields);
            Collections.reverse(fields);
            evaluateLine(fields);
        }
    }

    private void evaluateLine(List<Field> fields) {
        Field previous = Field.EMPTY_FIELD;
        int length = 0;
        for(Field field : fields) {
            if (field.getStone() == Stone.NONE) {
                addValueToMap(field, length * length);
                length = 0;
            } else if (field.getStone() != previous.getStone()) {
                length = 1;
            } else {
                length++;
            }
            previous = field;
        }
    }

    private void addValueToMap(Field field, int value) {
        if (valuesOfFields.containsKey(field)) {
            value += valuesOfFields.get(field);
        }
        valuesOfFields.put(field, value);
    }

    @Override
    public int getPriority() {
        return 5;
    }
}
