import java.security.cert.TrustAnchor;

public class Board {
    /**
     * default size of board
     */
    private static final int DEFAULT_SIZE =4;
    /**
     * size of board edge
     */
    private final int size;
    /**
     * board representation
     */
    private final Mark[][] board_rep;



    /**
     * default constructor with default size
     */
    public Board(){
       this.size= DEFAULT_SIZE;
       this.board_rep = new Mark[this.size][this.size];
       for (int i = 0; i < this.size; i++) {
           for (int j = 0; j < this.size; j++) {
               this.board_rep[i][j] = Mark.BLANK;
           }
       }
    }

    /**
     * constructor
     * @param size the size chosen
     */
    public Board(int size){
        this.size=size;
        this.board_rep = new Mark[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.board_rep[i][j] = Mark.BLANK;
            }
        }
    }

    /**
     *
     * @return size of board
     */
    public int getSize(){
        return size;
    }

    /**
     *
     * @param row coordinate
     * @param col coordinate
     * @return True if coordinates are valid on board and non taken else false
     */
    private boolean valid_coor(int row, int col){
        return 0 <= row && row < size && 0 <= col && col < size;
    }

    /**
     *
     * @return board representation
     */
    public Mark[][] getBoard(){
        return board_rep;
    }

    /**
     *
     * @param mark X or O
     * @param row coordinate
     * @param col coordinate
     * @return True if coordinates are valid, non taken and were signed - else false
     */
    public boolean putMark(Mark mark, int row, int col){
        if (valid_coor(row, col) && getMark(row, col) == Mark.BLANK) {
            board_rep[row][col] = mark;
            return true;
        }
        return false;
    }

    /**
     *
     * @param row coordinate
     * @param col coordinate
     * @return the mark inside
     */
    public Mark getMark(int row, int col){
        if(valid_coor(row, col)){
            return board_rep[row][col];
        }
        return Mark.BLANK;
    }
}
