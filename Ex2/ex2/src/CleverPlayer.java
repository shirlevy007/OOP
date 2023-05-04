import java.util.Random;

public class CleverPlayer implements Player{


    /**
     *
     * @param board of play
     * @param mark of player: X or O
     * @return true if we could continue a row streak, else false
     */
    private boolean try_row(Board board, Mark mark){
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize()-1; j++) {
                if (board.getMark(i,j) == mark) {
                    if (board.putMark(mark,i,j+1)){
                        return true;
                    }
                    if (board.putMark(mark,i,j-1)){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * plays the next turn upon valid coordinates that continues a row streak
     * @param board of the play
     * @param mark X or O
     */
    public void playTurn(Board board, Mark mark){
        if(try_row(board, mark)){
            return;
        }
        Random rand = new Random();
        int row = rand.nextInt(board.getSize());
        int col = rand.nextInt(board.getSize());
        while(!board.putMark(mark, row, col)){
            row = rand.nextInt(board.getSize());
            col = rand.nextInt(board.getSize());
        }
    }
}
