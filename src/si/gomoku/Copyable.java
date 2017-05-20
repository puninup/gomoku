package si.gomoku;

/**
 * @author Tomasz Urbas
 */
public interface Copyable<T extends Copyable<T>> {
    T copy();
}
