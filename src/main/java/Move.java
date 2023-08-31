public enum Move {
    LEFT(-1),
    RIGHT(1),
    STAY(0);

    private int step;

    Move(int i) {
        step = i;
    }

    public int getStep() {
        return step;
    }
}
