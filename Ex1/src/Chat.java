import java.util.Scanner;

public class Chat {
    public static void main(String[] args) {
//        ChatterBot[] bots = {null, null};
        ChatterBot[] bots = new ChatterBot[2];
        String[] illRep0 = new String[]{"what " + ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER, "say I should say "
                + ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER};
        String[] illRep1 = new String[]{"whaaat " + ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER, "say say "
                + ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER};
        String[] rep0 = new String[]{"You want me to say " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER
                + ", do you? alright: " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER};
        String[] rep1 = new String[]{"say " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER
                + "? okay: " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER};
        String name0 = "Victor";
        String name1 = "Jackson";
        bots[0] = new ChatterBot(name0, rep0, illRep0);
        bots[1] = new ChatterBot(name1, rep1, illRep1);

        String statement = "";
        String name = "";
        Scanner scanner = new Scanner(System.in);

//        while (true){
//            for (ChatterBot bot:bots) {
//                statement = bot.replyTo(statement);
//                System.out.print(statement);
//                scanner.nextLine();
//            }
//        }
        for (int i = 0; ; i++) {
            statement = bots[i % bots.length].replyTo(statement);
            name = bots[i % bots.length].getName();
            System.out.print(name + ": " + statement);
//            System.out.print(statement);
            scanner.nextLine();
        }
    }
}
