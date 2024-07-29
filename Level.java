public enum Level {
    EASY(12, 7, 10),
    MEDIUM(19, 11, 25),
    HARD(23, 15, 65);

    private final int rows;
    private final int cols;
    private final int nbrBombs;

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getNbrBombs() {
        return nbrBombs;
    }

    Level(int rows, int cols, int nbrBombs) {
        this.rows = rows;
        this.cols = cols;
        this.nbrBombs = nbrBombs;
    }
}
