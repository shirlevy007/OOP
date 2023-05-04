import java.util.Scanner;

class PlayerFactory {
//    private static final Scanner scanner = new Scanner(System.in);

    /**
     *
     * @param type of player: human\whatever\clever\genius
     * @return the correct player implementation
     */
    public Player buildPlayer (String type){
        Player player = null;
        switch (type.toLowerCase()){
            case "human":
                player = new HumanPlayer();
                break;
            case "whatever":
                player = new WhateverPlayer();
                break;
            case "clever":
                player = new CleverPlayer();
                break;
            case "genius":
                player = new GeniusPlayer();
                break;
        }
        return player;
    }
}
