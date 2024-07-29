
import java.util.Random;

public class Table {
    protected final int rows;
    protected final int cols;
    private final int[] brows;
    private final int[] bcols;
    protected String[][] table;
    protected final int nbrBombs;
    private ReferenceTable reference;

    Random random = new Random();

    public Table(Level level){
        this.rows = level.getRows();
        this.cols = level.getCols();
        this.table = new String[rows][cols];
        this.nbrBombs = level.getNbrBombs();
        this.brows = new int[nbrBombs];
        this.bcols = new int[nbrBombs];
    }

    public void newTable(){
        for(int i = 0; i < table.length; i++){
            for(int j = 0; j < table[0].length; j++){
                table[i][j] = " ";
            }
        }

        //fill in bombs and numbers
        for(int i = 0; i < nbrBombs; i++){
            int brow, bcol;
            brow = random.nextInt(rows);
            bcol = random.nextInt(cols);
            if(!isBomb(brow, bcol)) {
                brows[i] = brow;
                bcols[i] = bcol;
                table[brow][bcol] = "X";
                for(int k = brow-1; k <= brow+1; k++){
                    if(k<0 || k>=rows)
                        continue;
                    for(int l = bcol-1; l <= bcol+1; l++){
                        if(l<0 || l>=cols)
                            continue;
                        if(!isBomb(k, l)){
                            int val;
                            try {
                                val = Integer.parseInt(table[k][l]) +1;
                            }
                            catch (NumberFormatException e) {
                                val = 1;
                            }
                            table[k][l] = Integer.toString(val);
                        }
                    }
                }
            }
        }

    }

    public void setReference(ReferenceTable reference) {
        this.reference = reference;
        reference.newTable();
    }

    public void printTable(){
        int rowNbr = 1;
        String toPrint;
        System.out.println();
        System.out.print("    ");
        for(int i = 1; i <= cols; i++) {
            if(i<9)
                System.out.print(i + "  ");
            else
                System.out.print(i + " ");
        }
        System.out.println();

        System.out.print("   ");
        for(int i = 1; i <= cols; i++)
            System.out.print("---");

        for(int i = 0; i < rows; i++){
            System.out.println();
            if(rowNbr < 10)
                System.out.print(" " +rowNbr++ +" ");
            else
                System.out.print(rowNbr++ +" ");
            for(int j = 0; j < cols; j++){
                String val = reference.getVal(i,j);
                toPrint = switch (val) {
                    case "D" -> table[i][j]; //Display case
                    case "F" -> "F"; //Flag case
                    default -> "."; //Hide ("H") case
                };
                System.out.print("|");
                System.out.print(toPrint);
                System.out.print("|");
            }
        }
        System.out.println();

        System.out.print("   ");
        for(int i = 1; i <= cols; i++)
            System.out.print("---");

        System.out.println();
    }

    public boolean isValid(int row, int col){
        return 0 <= row && 0 <= col && row < rows && col < cols;
    }

    public boolean isFull() {
       return this.reference.isCompleted();
    }

    public void displayBombs(){
        for(int i = 0; i < this.nbrBombs; i++){
            reference.setD(brows[i], bcols[i]);
        }
    }

    public boolean isBomb(int row, int col) {
        return table[row][col].compareTo("X") == 0;
    }

    public boolean isSpace(int row, int col) {
        return table[row][col].compareTo(" ") == 0;
    }

    public void followSpace(int row, int col) {
        if(!this.isValid(row, col))
            return;

        if(this.reference.isDisplayed(row, col)){
            return;
        }

        if(!this.isSpace(row, col)){
            reference.setD(row, col);
            return;
        }

        reference.setD(row, col);

//        this.followSpace(row-1, col);
//
//        this.followSpace(row-1, col-1);
//
//        this.followSpace(row-1, col+1);
//
//
//        this.followSpace(row, col-1);
//
//        this.followSpace(row, col+1);
//
//        this.followSpace(row+1, col-1);
//
//        this.followSpace(row+1, col);
//
//        this.followSpace(row+1, col+1);

        for(int i = row-1; i <= row + 1; i++){
            for(int j = col-1; j <= col+1; j++){
                this.followSpace(i, j);
            }
        }
    }
}
