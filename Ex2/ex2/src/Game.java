public class Game {

    private static final int DEFAULT_WIN_STREAK = 3;

    /**
     * length of streak in order to win
     */
    private final int winStreak;
    /**
     * size of board edge
     */
    private final int size;
    /**
     * the array of Marks board represents
     */
    private final Board board;
    /**
     * player who plays first: human\whatever\clever\genius
     */
    private final Player playerX;
    /**
     * second player: human\whatever\clever\genius
     */
    private final Player playerO;
    /**
     * renderer of board
     */
    private final Renderer renderer;


    /**
     * @param playerX  human\whatever\clever\genius
     * @param playerO  human\whatever\clever\genius
     * @param renderer of board
     */
    public Game(Player playerX, Player playerO, Renderer renderer) {
        this.winStreak = DEFAULT_WIN_STREAK;
        this.board = new Board();
        this.size = this.board.getSize();
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
    }

    /**
     * @param playerX   human\whatever\clever\genius
     * @param playerO   human\whatever\clever\genius
     * @param size      of board
     * @param winStreak length of streak in order to win
     * @param renderer  of board
     */
    public Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer) {

        this.size=size;
        this.winStreak = Math.min(winStreak, size);
        this.board = new Board(size);
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
    }

    /**
     *
     * @param mark X or O
     * @return True if there is winning row streak of mark, else false
     */
    private boolean winningRow(Mark mark) {
        for (int i = 0; i < size; i++) {
            int streak_counter = 0;
            for (int j = 0; j < size; j++) {
                if (board.getBoard()[i][j] == mark) {
                    streak_counter++;
                    if (streak_counter==this.winStreak){
//                        System.out.println("Row win!");
                        return true;
                    }
                }
                else{
                    streak_counter=0;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param mark X or O
     * @return True if there is winning col streak of mark, else false
     */
    private boolean winningCol(Mark mark) {
        for (int i = 0; i < size; i++) {
            int streak_counter = 0;
            for (int j = 0; j < size; j++) {
                if (board.getBoard()[j][i] == mark) {
                    streak_counter++;
                    if (streak_counter==this.winStreak){
//                        System.out.println("Col win!");
                        return true;
                    }
                }
                else{
                    streak_counter=0;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param mark X or O
     * @return True if there is winning cross (left-down) streak of mark, else false
     */
    private boolean winningCrossRight(Mark mark) {
        for (int i = 0; i <= size-winStreak; i++) {
            int streak_counter = 0;
            for (int j = 0; j <= size-winStreak; j++) {
                if (board.getBoard()[i][j] == mark) {
                    streak_counter++;
                    int cross_i =i;
                    int cross_j =j;
                    for (int k = 1; k < winStreak; k++) {
                        cross_i++;
                        cross_j++;
                        if (board.getBoard()[cross_i][cross_j] == mark){
                            streak_counter++;
                        }
                        else {
                            break;
                        }
                    }
                    if(streak_counter==winStreak){
//                        System.out.println("Cross Right win!");
                        return true;
                    }
                }
                else{
                    streak_counter=0;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param mark X or O
     * @return True if there is winning cross (left-down) streak of mark, else false
     */
    private boolean winningCrossLeft(Mark mark) {
        for (int i = 0; i <= size-winStreak; i++) {
            int streak_counter = 0;
            for (int j = size-1; j >= winStreak-1; j--) {
                if (board.getBoard()[i][j] == mark) {
                    streak_counter++;
                    int cross_i =i;
                    int cross_j =j;
                    for (int k = 1; k < winStreak; k++) {
                        cross_i++;
                        cross_j--;
                        if (board.getBoard()[cross_i][cross_j] == mark){
                            streak_counter++;
                        }
                        else {
                            break;
                        }
                    }
                    if(streak_counter==winStreak){
//                        System.out.println("Cross Left win!");
                        return true;
                    }
                }
                else{
                    streak_counter=0;
                }
            }
        }
        return false;
    }

    /**
     * @return win streak length
     */
    public int getWinStreak() {
        return this.winStreak;
    }

    private boolean isWinner(Mark mark){
        return winningRow(mark) || winningCol(mark) || winningCrossRight(mark) || winningCrossLeft(mark);
    }

    /**
     * runs a full game until there's a winner by streak or until board is full
     * @return the winner X or O - if there's no winner return BLANK
     */
    Mark run() {
        int turnCounter = 0;
        int fullBoard = this.board.getSize()*this.board.getSize();
        renderer.renderBoard(board);
        while (true){
            playerX.playTurn(this.board, Mark.X);
            renderer.renderBoard(board);
            turnCounter++;
            if (isWinner(Mark.X)){
                return Mark.X;
            }
            if (turnCounter>=fullBoard){
                return Mark.BLANK;
            }
            playerO.playTurn(this.board, Mark.O);
            renderer.renderBoard(board);
            turnCounter++;
            if (isWinner(Mark.O)){
                return Mark.O;
            }
            if (turnCounter>=fullBoard){
                return Mark.BLANK;
            }
        }

    }


}
