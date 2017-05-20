package si.gomoku.game;

import si.gomoku.Copyable;
import si.gomoku.Direction;

import java.util.*;

/**
 * @author Tomasz Urbas
 */
public class Matrix<E extends Copyable<E>> implements Copyable<Matrix<E>> {
    private ArrayList<E> elements;
    private int dimension;

    Matrix(int dimension) {
        elements = new ArrayList<>(dimension * dimension);
        this.dimension = dimension;
    }

    public List<MatrixPartIndex> getAllPartIndexes() {
        Set<MatrixPartIndex> indexes = new HashSet<>();
        for (int row = 0; row < dimension; row++) {
            for (int column = 0; column < dimension; column++) {
                indexes.addAll(getPartIndexesFor(row, column));
            }
        }
        return new LinkedList<>(indexes);
    }

    public List<MatrixPartIndex> getPartIndexesFor(int row, int column) {
        List<MatrixPartIndex> indexes = new LinkedList<>();
        indexes.add(new MatrixPartIndex(MatrixPart.ROW, row));
        indexes.add(new MatrixPartIndex(MatrixPart.COLUMN, column));
        indexes.add(new MatrixPartIndex(MatrixPart.RIGHT_DIAGONAL, row + column));
        indexes.add(new MatrixPartIndex(MatrixPart.LEFT_DIAGONAL, dimension - row + column));
        return indexes;
    }

    public List<E> getPart(MatrixPartIndex index) {
        MatrixPart matrixPart = index.getPart();
        int partIndex = index.getIndex();
        return getPart(matrixPart, partIndex);
    }

    public List<E> getPart(MatrixPart part, int partIndex) {
        switch (part) {
            case ROW:
                return getRow(partIndex);
            case COLUMN:
                return getColumn(partIndex);
            case LEFT_DIAGONAL:
                return getLeftDiagonal(partIndex);
            case RIGHT_DIAGONAL:
                return getRightDiagonal(partIndex);
        }
        return new LinkedList<>();
    }

    public List<E> getRow(int row) {
        List<E> values = new LinkedList<>();
        for (int column = 0; column < dimension; column++) {
            E value = get(row, column);
            values.add(value);
        }
        return values;
    }

    public List<E> getColumn(int column) {
        List<E> values = new LinkedList<>();
        for (int row = 0; row < dimension; row++) {
            E value = get(row, column);
            values.add(value);
        }
        return values;
    }

    public List<E> getRightDiagonal(int row, int column) {
        int index = row + column;
        return getRightDiagonal(index);
    }

    public List<E> getRightDiagonal(int partIndex) {
        List<E> values = new LinkedList<>();
        for (int column = 0; column <= partIndex; column++) {
            int row = partIndex - column;
            if (row < dimension && column < dimension) {
                E value = get(row, column);
                values.add(value);
            }
        }
        return values;
    }

    public List<E> getLeftDiagonal(int row, int column) {
        int index = dimension - row + column;
        return getLeftDiagonal(index);
    }

    public List<E> getLeftDiagonal(int partIndex) {
        List<E> values = new LinkedList<>();
        for (int column = 0; column <= partIndex; column++) {
            int row = dimension - partIndex + column;
            if (row >= 0 && row < dimension
                    && column >= 0 && column < dimension) {
                E value = get(row, column);
                values.add(value);
            }
        }
        return values;
    }

    public Optional<E> getNeighbor(int row, int column, Direction direction) {
        row = row + direction.getRowStep();
        column = column + direction.getColumnStep();
        if (row >= 0 && row < dimension
                && column >= 0 && column < dimension) {
            return Optional.of(get(row, column));
        }
        return Optional.empty();
    }

    public E get(int row, int column) {
        int index = row * dimension + column;
        return elements.get(index);
    }

    public boolean add(E element) {
        return elements.add(element);
    }

    public List<E> asList() {
        return elements;
    }

    @Override
    public Matrix<E> copy() {
        Matrix<E> list = new Matrix<>(dimension);
        for(E element : elements) {
            list.elements.add(element.copy());
        }
        return list;
    }
}
