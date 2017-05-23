package si.gomoku.players;

import si.gomoku.game.Stone;

/**
 * @author Tomasz Urbas
 */
enum Level {
    MIN {
        @Override
        public Integer getWorstValue() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Level opposite() {
            return MAX;
        }

        @Override
        public boolean isBetter(int newValue, int oldValue) {
            return newValue < oldValue;
        }

        @Override
        public boolean isBetterOrEqual(int newValue, int oldValue) {
            return newValue <= oldValue;
        }

        @Override
        public Stone getStone(Stone myStone) {
            return myStone.opposite();
        }
    },
    MAX {
        @Override
        public Integer getWorstValue() {
            return Integer.MIN_VALUE;
        }

        @Override
        public Level opposite() {
            return MIN;
        }

        @Override
        public boolean isBetter(int newValue, int oldValue) {
            return newValue > oldValue;
        }

        @Override
        public boolean isBetterOrEqual(int newValue, int oldValue) {
            return newValue >= oldValue;
        }

        @Override
        public Stone getStone(Stone myStone) {
            return myStone;
        }
    };

    public abstract Integer getWorstValue();
    public abstract Level opposite();
    public abstract boolean isBetter(int newValue, int oldValue);
    public abstract boolean isBetterOrEqual(int newValue, int oldValue);
    public abstract Stone getStone(Stone myStone);
}