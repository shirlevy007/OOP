import java.util.*;

interface Player {
    /**
     * plays player's turn (as the type of player)
     * @param board of game
     * @param mark of player
     */
    public void playTurn(Board board, Mark mark);
}
