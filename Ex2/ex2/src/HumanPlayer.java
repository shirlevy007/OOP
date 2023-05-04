import java.util.Scanner;

public class HumanPlayer implements Player{
    private final Scanner scanner = new Scanner(System.in);

    /**
     * constructor
     */
    HumanPlayer(){}

    /**
     * plays the next turn upon valid coordinates the player chooses
     * @param board of the play
     * @param mark X or O
     */
    public void playTurn(Board board, Mark mark){
        System.out.printf(String.format("Player %s, type coordinates: ", mark));
        int coordinates = scanner.nextInt();
        int row = coordinates/10;
        int col = coordinates%10;
        scanner.nextLine();
        while(!(board.putMark(mark, row, col))){
            System.out.println("Invalid coordinates, type again: ");
            coordinates = scanner.nextInt();
            row = coordinates/10;
            col = coordinates%10;
            scanner.nextLine();
        }
    }
}
