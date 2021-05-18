package gameplaying_algorithms;

public class Board {

    private static final String TEXT_RESET = "\u001B[0m";
    private static final String TEXT_BLACK = "\u001B[30m";
    private static final String TEXT_RED = "\u001B[31m";
    private static final String TEXT_GREEN = "\u001B[32m";
    private static final String TEXT_YELLOW = "\u001B[33m";
    private static final String TEXT_BLUE = "\u001B[34m";
    private static final String TEXT_PURPLE = "\u001B[35m";
    private static final String TEXT_CYAN = "\u001B[36m";
    private static final String TEXT_WHITE = "\u001B[37m";


    private final int sizeX;
    private final int sizeY;
    private Cell[][] cells;
    private boolean whiteWon = false;
    private boolean blackWon = false;
    private int numberOfFreeSpaces;
    private boolean isFinished = false;

    public Board(int x, int y) {
        sizeX = x;
        sizeY = y;
        cells = new Cell[x][y];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
        numberOfFreeSpaces = x * y;
    }

    public boolean nextStep(int x, int y, Color c) {
        if (cells[x][y].isEmpty()) {
            cells[x][y].addPiece(c);
            checkForWinner(x, y, c);
            numberOfFreeSpaces--;
            return true;
        }
        return false;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void checkForWinner(int y, int x, Color checkingColor) {

        int minY = Math.max(y - 4, 0);
        int maxY = Math.min(y + 4, sizeY - 1);
        int maxX = Math.min(x + 4, sizeX - 1);
        int minX = Math.max(x - 4, 0);


        int counter = 0;
        if (maxY - minY >= 4) {
            for (int currY = minY; currY <= maxY; currY++) {
                Color c = cells[currY][x].getColor();
                if (c == checkingColor) {
                    counter++;
                    if (counter == 5) {
                        setWinner(checkingColor);
                        return;
                    }
                } else {
                    counter = 0;
                }
            }
        }

        counter = 0;
        if (maxX - minX >= 4) {
            for (int currX = minX; currX <= maxX; currX++) {
                Color c = cells[y][currX].getColor();
                if (c == checkingColor) {
                    counter++;
                    if (counter == 5) {
                        setWinner(checkingColor);
                        return;
                    }
                } else {
                    counter = 0;
                }
            }
        }

        int deltaMinY = y - minY;
        int deltaMaxY = maxY - y;
        int deltaMaxX = maxX - x;
        int deltaMinX = x - minX;

        minX = x - Math.min(deltaMaxY, deltaMinX);
        maxY = y + Math.min(deltaMaxY, deltaMinX);

        maxX = x + Math.min(deltaMinY, deltaMaxX);
        minY = y - Math.min(deltaMinY, deltaMaxX);

        if (maxX - minX >= 4) {
            for (int currX = minX, currY = maxY; currX <= maxX; currX++, currY--) {
                Color c = cells[currY][currX].getColor();
                if (c == checkingColor) {
                    counter++;
                    if (counter == 5) {
                        setWinner(checkingColor);
                        return;
                    }
                } else {
                    counter = 0;
                }
            }
        }

        minX = x - Math.min(deltaMinX, deltaMinY);
        minY = y - Math.min(deltaMinX, deltaMinY);

        maxX = x + Math.min(deltaMaxX, deltaMaxY);
        maxY = y + Math.min(deltaMaxX, deltaMaxY);

        if (maxX - minX >= 4){
            for(int currX = minX, currY = minY; currX <= maxX; currX++, currY++){
                Color c = cells[currY][currX].getColor();
                if (c == checkingColor) {
                    counter++;
                    if (counter == 5) {
                        setWinner(checkingColor);
                        return;
                    }
                } else {
                    counter = 0;
                }
            }
        }
    }

    //todo improve
    public int scoreOfTheBoard(Color c) {
        if (c == Color.BLACK && blackWon) {
            return 1000;
        }
        if (c == Color.BLACK && whiteWon) {
            return -1000;
        }
        if (c == Color.WHITE && blackWon) {
            return -1000;
        }
        if (c == Color.WHITE && whiteWon) {
            return 1000;
        }
        return 0;
    }

    public String printBoard() {
        String s = "";
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                Color col = cells[i][j].getColor();
                if (col != null) {
                    if (col.equals(Color.WHITE))
                        System.out.print(TEXT_BLUE + "W\t" + TEXT_RESET);
                    else System.out.print(TEXT_RED + "B\t" + TEXT_RESET);
                } else {
                    System.out.print(TEXT_WHITE + (i * 15 + j) + "\t" + TEXT_RESET);
                }
            }
            System.out.println();
        }
        return s;
    }

    @Override
    public Board clone() {
        Board b = new Board(sizeX, sizeY);
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Color c = cells[i][j].getColor();
                if (c != null) {
                    b.cells[i][j].addPiece(c);
                }
            }
        }
        return b;
    }

    public int numOfFreePlaces() {
        return numberOfFreeSpaces;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public boolean removePiece(int x, int y) {
        whiteWon = false;
        blackWon = false;
        //todo maybe check of this cell is empty
        //(this method is called only in MinMax -> piece would be ALWAYS removed)
        numberOfFreeSpaces++;
        return cells[x][y].removePiece();
    }

    public void setWinner(Color color) {
        if (color == Color.WHITE) {
            whiteWon = true;
        } else {
            blackWon = true;
        }
        isFinished = true;
    }

}
