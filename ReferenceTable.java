public class ReferenceTable extends Table {
    private int nbrCases;

    public ReferenceTable(Level level) {
        super(level);

        this.nbrCases = this.rows * this.cols;
    }

    @Override
    public void newTable(){
        for(int i = 0; i < table.length; i++){
            for(int j = 0; j < table[0].length; j++){
                table[i][j] = "H";
            }
        }
    }

    public String getVal(int row, int col){
        return table[row][col];
    }

    public void setD(int row, int col){ //set Display
        if(this.isDisplayed(row, col))
            return;
        setVal(row, col, "D");
        nbrCases--;
    }

    public void setF(int row, int col){ //set Flag
        setVal(row, col, "F");
    }

    public void rmvF(int row, int col){ //remove Flag
        setVal(row, col, "H");
    }

    public void setVal(int row, int col, String val){
        table[row][col] = val;
    }

    public boolean isFlagged (int row, int col){
        return table[row][col].compareTo("F") == 0;
    }

    public boolean canBeFlagged(int row, int col) {
        return super.isValid(row, col) && !this.isDisplayed(row, col);
    }

    public boolean isDisplayed(int row, int col) {
        return table[row][col].compareTo("D")==0;
    }

    public boolean isCompleted(){
        return this.nbrCases <= this.nbrBombs;
    }
}
