package si.gomoku.components;

import javafx.scene.paint.Color;

/**
 * @author Tomasz Urbas
 */
public enum Stone {
    DARK(Color.BLACK),
    LIGHT(Color.WHITE),
    NONE(Color.TRANSPARENT);

    Color color;

    Stone(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
