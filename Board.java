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

    public Board(int x, int y) {
        sizeX = x;
        sizeY = y;
        cells = new Cell[x][y];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public boolean nextStep(int x, int y, Color c) {
        if (cells[x][y].isEmpty()) {
            cells[x][y].addPiece(c);
            checkForWinner(x, y, c);
            return true;
        }
        return false;
    }

    public boolean isFinished() {
        return blackWon || whiteWon;
    }

    public void checkForWinner(int x, int y, Color checkingColor) {

        int counter = 0;
        int leftX = x - 4;
        if (leftX >= 0) {
            for (int currX = x; currX >= leftX; currX--) {
                Color c = cells[currX][y].getColor();
                if (c == checkingColor) {
                    counter++;
                } else
                    counter = 0;
            }
        }
        if (counter == 5) {
            setWinner(checkingColor);
            return;
        }
        int rightX = x + 4;
        if (rightX < sizeX) {
            for (int currX = x; currX <= rightX; currX++) {
                Color c = cells[currX][y].getColor();
                if (c == checkingColor) {
                    counter++;
                } else
                    break;
            }
        }
        if (counter == 5) {
            setWinner(checkingColor);
            return;
        }


        counter = 0;
        int upY = y + 4;
        if (upY < sizeY) {
            for (int currY = y; currY <= upY; currY++) {
                Color c = cells[x][currY].getColor();
                if (c == checkingColor) {
                    counter++;
                } else
                    break;
            }
        }
        if (counter == 5) {
            setWinner(checkingColor);
            return;
        }


        counter = 0;
        int downY = y - 4;
        if (downY >= 0) {
            for (int currY = x; currY >= downY; currY--) {
                Color c = cells[x][currY].getColor();
                if (c == checkingColor) {
                    counter++;
                } else
                    break;
            }
        }
        if (counter == 5) {
            setWinner(checkingColor);
            return;
        }

        counter = 0;
        if (upY < sizeY && leftX >= 0) {
            for (int currX = x, currY = y; currY <= upY; currX--, currY++) {
                Color c = cells[currX][currY].getColor();
                if (c == checkingColor) {
                    counter++;
                } else
                    break;
            }
        }
        if (counter == 5) {
            setWinner(checkingColor);
            return;
        }


        counter = 0;
        if (upY < sizeY && rightX < sizeX) {
            for (int currX = x, currY = y; currY <= upY; currX++, currY++) {
                Color c = cells[currX][currY].getColor();
                if (c == checkingColor) {
                    counter++;
                } else
                    break;
            }
        }
        if (counter == 5) {
            setWinner(checkingColor);
            return;
        }

        counter = 0;
        if (downY >= 0 && leftX >= 0) {
            for (int currX = x, currY = y; currY >= downY; currX--, currY--) {
                Color c = cells[currX][currY].getColor();
                if (c == checkingColor) {
                    counter++;
                } else
                    break;
            }
        }
        if (counter == 5) {
            setWinner(checkingColor);
            return;
        }

        counter = 0;
        if (downY >= 0 && rightX < sizeX) {
            for (int currX = x, currY = y; currY >= downY; currX++, currY--) {
                Color c = cells[currX][currY].getColor();
                if (c == checkingColor) {
                    counter++;
                } else
                    break;
            }
        }
        if (counter == 5) {
            setWinner(checkingColor);
            return;
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
        int counter = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].isEmpty()) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public boolean removePiece(int x, int y) {
        whiteWon = false;
        blackWon = false;
        return cells[x][y].removePiece();
    }

    public void setWinner(Color color){
        if(color == Color.WHITE){
            whiteWon = true;
        }
        else {
            blackWon = true;
        }
    }

}
