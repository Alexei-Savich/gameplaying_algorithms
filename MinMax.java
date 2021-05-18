package gameplaying_algorithms;

import java.util.ArrayList;
import java.util.Random;

public class MinMax {

    private final Board startingBoard;
    private final Color color;
    private final int depth;

    public MinMax(Board startingBoard, Color color, int depth) {
        this.startingBoard = startingBoard.clone();
        this.color = color;
        this.depth = depth;
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
        //todo double for
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
                i.setValue(evaluate(depth - 1, startingBoard, nextColor(color), false));
            }
            startingBoard.removePiece(i.getX(), i.getY());
        }

    }

    public int evaluate(int levelsLeft, Board b, Color curr, boolean isMax) {
        Cell[][] cells = b.getCells();
        int returnValue = Integer.MIN_VALUE;
        for (int x = 0; x < cells.length; x++) {
//            if (levelsLeft > 1) {
//                System.out.println("Level " + levelsLeft + ", X =" + x + ", val = " + returnValue);
//            }
            for (int y = 0; y < cells[x].length; y++) {
                if (cells[x][y].isEmpty()) {
                    b.nextStep(x, y, curr);
                    if (x == 10 && y == 14) {
                        System.out.println(b.printBoard());
                    }
                    int val;
                    if (levelsLeft == 0) {
                        //System.out.println(b.printBoard());
                        val = b.scoreOfTheBoard(color);
                        //System.out.println(val);
                    } else {
                        Color next = nextColor(curr);
                        boolean newIsMax = minMaxChange(isMax);
                        val = evaluate(levelsLeft - 1, b, next, newIsMax);
                    }
                    if (val == Integer.MIN_VALUE) {
                        val = b.scoreOfTheBoard(color);
                        //System.out.println("Error??? " + levelsLeft + ", X " + x + " y = " + y);
                    }
//                    if (val != Integer.MIN_VALUE && val != 0) {
//                        System.out.println("Error??? " + levelsLeft + ", X " + x + " y = " + y + ", val =" + val);
//                    }

                    if (returnValue == Integer.MIN_VALUE) {
                        returnValue = val;
                    } else {
                        if (isMax) {
                            if (val > returnValue) {
                                returnValue = val;
                            }
                        } else {
                            if (val < returnValue) {
                                returnValue = val;
                            }
                        }
                    }
                    b.removePiece(x, y);
                }
            }
        }
        return returnValue;
    }

    private Color nextColor(Color curr) {
        Color next;
        if (curr.equals(Color.WHITE)) {
            next = Color.BLACK;
        } else {
            next = Color.WHITE;
        }
        return next;
    }

    private boolean minMaxChange(boolean curr) {
        boolean next;
        if (curr) {
            next = false;
        } else {
            next = true;
        }
        return next;
    }

}
