package si.gomoku.game;

/**
 * @author Tomasz Urbas
 */
public class MatrixPartIndex {
    private MatrixPart part;
    private int index;

    MatrixPartIndex(MatrixPart part, int index) {
        this.part = part;
        this.index = index;
    }

    public MatrixPart getPart() {
        return part;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatrixPartIndex that = (MatrixPartIndex) o;

        if (index != that.index) return false;
        return part == that.part;
    }

    @Override
    public int hashCode() {
        int result = part != null ? part.hashCode() : 0;
        result = 31 * result + index;
        return result;
    }
}