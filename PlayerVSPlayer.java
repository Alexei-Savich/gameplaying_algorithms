package gameplaying_algorithms;

public class PlayerVSPlayer {

    public static void main(String[] args) {

        Gomoku game = new Gomoku();
        System.out.println(game.getBoard().printBoard());
        int counter = 0;
        while (!game.getBoard().isFinished() && counter < 224) {
            if (counter % 2 == 0) {
                System.out.println("Turn of the white:");
                game.nextStep(Color.WHITE);
            } else {
                System.out.println("Turn of the black:");
                game.nextStep(Color.BLACK);
            }
            System.out.println(game.getBoard().printBoard());
            //System.out.println(game.getBoard().scoreOfTheBoard(Color.BLACK));
            counter++;
        }
        if (counter == 225) {
            System.out.println("It is a draw!");
        } else {
            counter--;
            if (counter % 2 == 0) {
                System.out.println("White won!");
            } else {
                System.out.println("Black won!");
            }
        }
    }
}
