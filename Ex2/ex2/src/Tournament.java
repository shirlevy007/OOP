import java.util.Arrays;

public class Tournament {

    /**
     * number of rounds to be played
     */
    private final int rounds;
    /**
     * renderer: console\none
     */
    private final Renderer renderer;
    /**
     * array of players: human\whatever\clever\genius
     */
    private final Player[] players;


    /**
     *
     * @param rounds number of rounds to be played
     * @param renderer of board: none\console
     * @param players out of: human\whatever\clever\genius
     */
    public Tournament(int rounds, Renderer renderer, Player[] players){
        this.rounds = rounds;
        this.renderer = renderer;
        this.players = players;
    }

    /**
     *
     * @param size of board
     * @param winStreak length of streak in order to win
     * @param playerNames out of: human\whatever\clever\genius
     */
    public void playTournament(int size, int winStreak, String[] playerNames){
        Mark winner;
        Player temp;
        int ties;
        Player playerX = players[0];
        Player playerO = players[1];
        int [] winsCounter = new int[2];
        for (int i = 0; i < this.rounds; i++) {
            Game game = new Game(playerX, playerO, size, winStreak, renderer);
            winner = game.run();
            if(winner!=Mark.BLANK)
            {
                if(winner == Mark.X){
                    winsCounter[i%2]++;
                }
                else {
                    winsCounter[(1+i)%2]++;
                }
            }
            temp = playerX;
            playerX = playerO;
            playerO = temp;
        }
        System.out.println("######### Results #########");
        System.out.println("Player 1, " + playerNames[0]+ " won: " + winsCounter[0] +" rounds");
        System.out.println("Player 2, " + playerNames[1]+ " won: " + winsCounter[1] +" rounds");
        ties = rounds-winsCounter[0]-winsCounter[1];
        System.out.print("Ties: " + ties);

    }

    public static void main (String [] args){
        int rounds = Integer.parseInt(args[0]);
        int size = Integer.parseInt(args[1]);
        int winStreak = Integer.parseInt(args[2]);
        RendererFactory rendererFactory = new RendererFactory();
        Renderer renderer = rendererFactory.buildRenderer(args[3],size );
        PlayerFactory playerFactory = new PlayerFactory();
        Player playerX = playerFactory.buildPlayer(args[4]);
        if (playerX==null){
            System.out.println("Choose a player, and start again");
            System.out.print("The players: [human, clever, whatever, genius]");
            return;
        }
        Player playerO = playerFactory.buildPlayer(args[5]);
        if (playerO==null){
            System.out.println("Choose a player, and start again");
            System.out.print("The players: [human, clever, whatever, genius]");
            return;
        }
        Player[] players = new Player [2];
        players[0] = playerX;
        players[1] = playerO;
        String [] playersStr = Arrays.copyOfRange(args, 4, 6);

        Tournament tournament = new Tournament(rounds, renderer, players);
        tournament.playTournament(size, winStreak, playersStr);




    }
}
