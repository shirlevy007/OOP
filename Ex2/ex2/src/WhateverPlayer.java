import java.util.Random;
import java.lang.Math;

public class WhateverPlayer implements Player{



    /**
     * plays the next turn upon valid coordinates the player randomly chooses
     * @param board of the play
     * @param mark X or O
     */
    public void playTurn(Board board, Mark mark){
        Random rand = new Random();
        int row = rand.nextInt(board.getSize());
        int col = rand.nextInt(board.getSize());
        while(!board.putMark(mark, row, col)){
            row = rand.nextInt(board.getSize());
            col = rand.nextInt(board.getSize());
        }
    }
}
