package si.gomoku.qualifiers;

import si.gomoku.game.Board;
import si.gomoku.game.Field;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tomasz Urbas
 */
public class LastMoveTracker implements QualityHeuristic {

    @Override
    public List<Field> qualify(List<Field> fields, Board board) {
        Field lastMove = board.getLastMove();
        return getSpiralFrom(lastMove.getRow(), lastMove.getColumn(), board).stream()
                .filter(fields::contains)
                .collect(Collectors.toList());
    }

    private List<Field> getSpiralFrom(int centerRow, int centerColumn, Board board) {
        boolean found = true;
        int level = 0;

        int row = centerRow;
        int column = centerColumn;

        List<Field> fields = new LinkedList<>();
        while (found) {
            found = false;
            level++;

            Field field = board.getField(row, column);
            if (field != Field.EMPTY_FIELD) {
                found = true;
                fields.add(field);
            }
            while (column < centerColumn + level) {
                column++;
                field = board.getField(row, column);
                if (field != Field.EMPTY_FIELD) {
                    found = true;
                    fields.add(field);
                }
            }
            while (row < centerRow + level) {
                row++;
                field = board.getField(row, column);
                if (field != Field.EMPTY_FIELD) {
                    found = true;
                    fields.add(field);
                }
            }
            while (column > centerColumn - level) {
                column--;
                field = board.getField(row, column);
                if (field != Field.EMPTY_FIELD) {
                    found = true;
                    fields.add(field);
                }
            }
            while (row > centerRow - level) {
                row--;
                field = board.getField(row, column);
                if (field != Field.EMPTY_FIELD) {
                    found = true;
                    fields.add(field);
                }
            }

            column++;
        }
        return fields;
    }

    @Override
    public int getPriority() {
        return 5;
    }
}
