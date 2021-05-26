package gameplaying_algorithms;

import java.util.ArrayList;
import java.util.Random;

public class MinMax {

    private final Board startingBoard;
    private final Color color;
    private final int depth;
    private final Color opponent;

    public MinMax(Board startingBoard, Color color, int depth) {
        this.startingBoard = startingBoard;
        this.color = color;
        this.depth = depth;
        if (color.equals(Color.WHITE)) {
            opponent = Color.BLACK;
        } else {
            opponent = Color.WHITE;
        }
    }

    public String nextStep() {
        InformationAboutStep[] info = generateFirstLevel();
        evaluateFirstLevel(info);
        int max = Integer.MIN_VALUE;
        for (InformationAboutStep i : info) {
            if (i.getValue() > max) {
                max = i.getValue();
            }
        }
        ArrayList<InformationAboutStep> best = new ArrayList<>();
        for (InformationAboutStep i : info) {
            if (i.getValue() == max) {
                best.add(i);
            }
        }
        Random rand = new Random();
        int nextStep = rand.nextInt(best.size());
        int x = best.get(nextStep).getX();
        int y = best.get(nextStep).getY();
        int result = x * 15 + y;
        return result + "";
    }

    public InformationAboutStep[] generateFirstLevel() {
        InformationAboutStep[] info = new InformationAboutStep[startingBoard.numOfFreePlaces()];
        int x = 0;
        int y = 0;
        Cell[][] cells = startingBoard.getCells();
        //todo maybe double for
        for (int currElement = 0; currElement < info.length; currElement++) {
            while (!cells[x][y].isEmpty()) {
                if (y == 14) {
                    y = 0;
                    x++;
                } else {
                    y++;
                }
            }
            info[currElement] = new InformationAboutStep(x, y, color);
            if (y == 14) {
                y = 0;
                x++;
            } else {
                y++;
            }
        }
        return info;
    }

    public void evaluateFirstLevel(InformationAboutStep[] info) {
        for (InformationAboutStep i : info) {
            startingBoard.nextStep(i.getX(), i.getY(), color);
            if (startingBoard.isFinished()) {
                i.setValue(startingBoard.scoreOfTheBoard(color));
            } else {
                i.setValue(evaluateMin(depth - 1, startingBoard));
            }
            startingBoard.removePiece(i.getX(), i.getY());
            System.out.println("score: " + i.getValue() + "; coordinates:" + i.getX() + ", " + i.getY());
        }

    }

    public int evaluateMax(int levelsLeft, Board b) {
        //todo 1d array
        Cell[][] cells = b.getCells();
        int returnValue = Integer.MIN_VALUE;
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                //todo set instead (numbers)
                if (cells[x][y].isEmpty()) {
                    b.nextStep(x, y, color);
                    int val;
                    //todo moving outside of for
                    if (levelsLeft == 0 || b.isFinished() || b.numOfFreePlaces() == 0) {
                        val = b.scoreOfTheBoard(color);
                    }
                    else {
                        val = evaluateMin(levelsLeft - 1, b);
                    }
                    if (returnValue == Integer.MIN_VALUE) {
                        returnValue = val;
                    } else {
                        if (val > returnValue) {
                            returnValue = val;
                        }
                    }
                    b.removePiece(x, y);
                }
            }
        }
        return returnValue;
    }

    public int evaluateMin(int levelsLeft, Board b) {
        //todo 1d array
        Cell[][] cells = b.getCells();
        int returnValue = Integer.MIN_VALUE;
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                //todo set instead (numbers)
                if (cells[x][y].isEmpty()) {
                    b.nextStep(x, y, opponent);
                    int val;
                    //todo moving outside of for
                    if (levelsLeft == 0 || b.isFinished() || b.numOfFreePlaces() == 0) {
                        val = b.scoreOfTheBoard(color);
                    }
                    else {
                        val = evaluateMax(levelsLeft - 1, b);
                    }
                    if (returnValue == Integer.MIN_VALUE) {
                        returnValue = val;
                    } else {
                        if (val < returnValue) {
                            returnValue = val;
                        }
                    }
                    b.removePiece(x, y);
                }
            }
        }
        return returnValue;
    }

}
