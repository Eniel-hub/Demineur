import java.util.Scanner;

public class Game {
    private int flags;
    private final Table table;
    private final Level level;
    private boolean isFlagging = false;
    private final ReferenceTable referenceTable;

    Scanner scanner = new Scanner(System.in);

    public Game(Level level) {
        this.level = level;
        referenceTable = new ReferenceTable(this.level);
        table = new Table(this.level);
        table.newTable();
        table.setReference(referenceTable);
        flags = this.level.getNbrBombs();

    }

    public void open(int row, int col){
        referenceTable.setD(row, col);
    }

    public void flag(int row, int col){
        if(referenceTable.isFlagged(row, col)) {
            referenceTable.rmvF(row, col);
            flags++;
        }
        else {
            if(flags==0){
                System.out.println("Sorry, you can't flag anymore");
                this.play();
                return;
            }
            referenceTable.setF(row, col);
            flags--;
        }
    }

    public void print(){
        System.out.println("Number of bombs: "+level.getNbrBombs());
        System.out.println("Number of flags left: "+this.flags);
        table.printTable();
    }

    public void play(){
        this.print();

        String flagging = isFlagging ? "You are flagging" : "You are demining";
        System.out.println(flagging);
        String scan = "";
        do {
            System.out.print("Do you want to change? (y or n) : ");
            scan = scanner.next().toLowerCase();
            System.out.println();
        }while (scan.compareTo("y") != 0 && scan.compareTo("n") != 0);
        if(scan.compareTo("y") == 0)
            isFlagging = !isFlagging;
        flagging = isFlagging ? "You are flagging" : "You are demining";
        System.out.println(flagging);

        System.out.print("enter row : ");
        while(!scanner.hasNextInt()) {
            System.out.println();
            System.out.print("enter row : ");
            scanner.next();
        }
        int row = scanner.nextInt()-1;
        System.out.println();


        System.out.print("enter col : ");
        while(!scanner.hasNextInt()) {
            System.out.println();
            System.out.print("enter col : ");
            scanner.next();
        }
        int col = scanner.nextInt()-1;
        System.out.println();

        if (!table.isValid(row, col)) {
            System.out.println("wrong entries");
            this.play();
            return;
        }

        if(isFlagging){
            if(!referenceTable.canBeFlagged(row, col)){
                System.out.println("you can't flag this case");
                this.play();
                return;
            }
            this.flag(row, col);
            this.play();
        }
        else {
            if(referenceTable.isDisplayed(row, col)){
                System.out.println("you can't open this case");
                this.play();
                return;
            }
            this.flags += referenceTable.isFlagged(row, col)? 1 : 0;

            if(table.isSpace(row, col)){
                table.followSpace(row, col);
            }

            this.open(row, col);

            if (table.isBomb(row, col)) {
                this.endGame(false);
                return;
            }

            if (table.isFull()) {
                this.endGame(true);
                return;
            }

            this.play();
        }
    }

    private void endGame(boolean isWon) {
        if(isWon){
            System.out.println("Game Won");
            System.out.println("You found all the bombs");
        } else {
            System.out.println("Game Lost");
            System.out.println("Oh No!!! A bomb exploded");
        }
        this.table.displayBombs();
        this.print();
    }
}
