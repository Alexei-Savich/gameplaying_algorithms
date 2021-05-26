package gameplaying_algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

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
            info[currElement] = new InformationAboutStep(x, y);
            if (y == 14) {
                y = 0;
                x++;
            } else {
                y++;
            }
        }
        return info;
    }

    public int evaluateMax(int levelsLeft, Board b) {
        Cell[][] cells = b.getCells();
        int returnValue = Integer.MIN_VALUE;
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                if (cells[x][y].isEmpty()) {
                    b.nextStep(x, y, color);
                    int val;
                    if (levelsLeft == 0 || b.isFinished() || b.numOfFreePlaces() == 0) {
                        val = b.scoreOfTheBoard(color);
                    } else {
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
        Cell[][] cells = b.getCells();
        int returnValue = Integer.MIN_VALUE;
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                if (cells[x][y].isEmpty()) {
                    b.nextStep(x, y, opponent);
                    int val;
                    if (levelsLeft == 0 || b.isFinished() || b.numOfFreePlaces() == 0) {
                        val = b.scoreOfTheBoard(color);
                    } else {
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

    static List<Board> boards = new ArrayList<>();

    public class Evaluate implements Callable<InformationAboutStep> {
        InformationAboutStep step;
        Board board;
        int number;

        public Evaluate(InformationAboutStep step, Board board, int num) {
            this.step = step;
            this.board = board;
            this.number = num;
        }

        @Override
        public InformationAboutStep call() throws Exception {
            Board localBoard = getBoard();
            localBoard.nextStep(step.getX(), step.getY(), color);
            int score = localBoard.scoreOfTheBoard(color);
            if (score == 1000)
                step.setValue(1000);
            else
                step.setValue(evaluateMin(depth - 1, localBoard));
            System.out.println("Info " + number + ", X = " + step.getX() + ", Y =" + step.getY() + ", v = " + step.getValue());
            localBoard.removePiece(step.getX(), step.getY());
            setBoard(localBoard);
            return step;
        }

        public synchronized Board getBoard() {
            if (boards.size() > 0) {
                return boards.remove(boards.size() - 1);
            }
            return board.clone();
        }

        public synchronized void setBoard(Board board) {
            boards.add(board);
        }
    }

    public void evaluateFirstLevel(InformationAboutStep[] info) {
        ExecutorService threadPool = Executors.newFixedThreadPool(6);
        List<Future<InformationAboutStep>> futures = new ArrayList<>();
        int count = 0;
        long start = System.currentTimeMillis();
        boards.clear();
        for (InformationAboutStep step : info) {
            Future<InformationAboutStep> future = threadPool.submit(new Evaluate(step, startingBoard, count++));
            futures.add(future);
        }
        for (Future<InformationAboutStep> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Work time " + (end - start) / 1000. + ", sec");
    }

}
