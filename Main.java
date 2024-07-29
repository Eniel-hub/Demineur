/*
Demineur Game
* July 16, 2024
* July 17, 2024
* JUly 19, 2024
* July 22, 2026
* author: Eniel Leba
* tableau[row][col]
*/
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Demineur");
        String res = "Yes";
        do {
            System.out.println("enter the level you would like to play (easy, medium, hard): ");
            Level level = switch (scanner.next().toUpperCase()) {
                case "HARD" -> level = Level.HARD;
                case "MEDIUM" -> level = Level.MEDIUM;
                default -> level = Level.EASY;
            };
            Game game = new Game(level);
            System.out.println("New game level: " + level);
            game.play();

            System.out.println("Do you want to play again? enter Yes or No");
            scanner.next();
        } while (res.toLowerCase().compareTo("no") != 0);
    }
}