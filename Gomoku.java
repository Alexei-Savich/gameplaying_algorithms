package gameplaying_algorithms;

import java.util.Scanner;

public class Gomoku {

    private Board board;
    private final int sizeX = 15;
    private final int sizeY = 15;


    public Gomoku() {
        board = new Board(sizeX, sizeY);
    }

    public void nextStep(Color c) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        int coordinates = stringToCoordinates(s);
        while (coordinates == -1) {
            System.out.println("Invalid input!");
            s = in.next();
            coordinates = stringToCoordinates(s);
        }
        while (!board.nextStep(coordinates / 15, coordinates % 15, c)) {
            System.out.println("This field is occupied");
            s = in.nextLine();
            coordinates = stringToCoordinates(s);
            while (coordinates == -1) {
                System.out.println("Invalid input!");
                s = in.next();
                coordinates = stringToCoordinates(s);
            }
        }
    }

    public void nextStepAI(Color c, String s) {
        int coordinates = stringToCoordinates(s);
        while (coordinates == -1) {
            System.out.println("Invalid input!");
            coordinates = stringToCoordinates(s);
        }
        while (!board.nextStep(coordinates / 15, coordinates % 15, c)) {
            System.out.println("This field is occupied");
            coordinates = stringToCoordinates(s);
            while (coordinates == -1) {
                System.out.println("Invalid input!");
                coordinates = stringToCoordinates(s);
            }
        }
    }

    private int stringToCoordinates(String s) {
        if (s.matches("[0-9]{1,3}")) {
            int toReturn = Integer.parseInt(s);
            if (toReturn >= 0 && toReturn < 225) {
                return toReturn;
            }
        }
        return -1;
    }

    public Board getBoard() {
        return board;
    }
}
