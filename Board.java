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
            return true;
        }
        return false;
    }

    public boolean isFinished() {
        return rowsCheck() || columnsCheck() || diagonalsCheck();
    }

    //todo improve
    public int evaluate(Color c){
        //todo
        isFinished();
        if(c == Color.BLACK && blackWon){
            return 1000;
        }
        if(c == Color.BLACK && whiteWon){
            return -1000;
        }
        if(c == Color.WHITE && blackWon){
            return -1000;
        }
        if(c == Color.WHITE && whiteWon){
            return 1000;
        }
        return 0;
    }
    private boolean rowsCheck() {
        int blackCounter;
        int whiteCounter;
        for (int i = 0; i < sizeX; i++) {
            whiteCounter = 0;
            blackCounter = 0;
            for (int j = 0; j < sizeY; j++) {
                Color c = cells[i][j].getColor();
                if (c == null) {
                    blackCounter = 0;
                    whiteCounter = 0;
                } else {
                    if (c.equals(Color.WHITE)) {
                        blackCounter = 0;
                        whiteCounter++;
                    } else {
                        whiteCounter = 0;
                        blackCounter++;
                    }
                }
                if (whiteCounter == 5 ) {
                    whiteWon = true;
                    return true;
                }
                if(blackCounter == 5){
                    blackWon = true;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean columnsCheck() {
        int blackCounter;
        int whiteCounter;
        for (int i = 0; i < sizeY; i++) {
            whiteCounter = 0;
            blackCounter = 0;
            for (int j = 0; j < sizeX; j++) {
                Color c = cells[j][i].getColor();
                if (c == null) {
                    blackCounter = 0;
                    whiteCounter = 0;
                } else {
                    if (c.equals(Color.WHITE)) {
                        blackCounter = 0;
                        whiteCounter++;
                    } else {
                        whiteCounter = 0;
                        blackCounter++;
                    }
                }
                if (whiteCounter == 5 ) {
                    whiteWon = true;
                    return true;
                }
                if(blackCounter == 5){
                    blackWon = true;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean diagonalsCheck() {
        return downCheckX() || downCheckY() || upCheckX() || upCheckY();
    }

    private boolean downCheckX() {
        int blackCounter;
        int whiteCounter;
        for (int i = 0; i < sizeX - 4; i++) {
            blackCounter = 0;
            whiteCounter = 0;
            for (int k = i, j = 0; k < sizeX && j < sizeY; k++, j++) {
                Color c = cells[k][j].getColor();
                if (c == null) {
                    blackCounter = 0;
                    whiteCounter = 0;
                } else {
                    if (c.equals(Color.WHITE)) {
                        blackCounter = 0;
                        whiteCounter++;
                    } else {
                        whiteCounter = 0;
                        blackCounter++;
                    }
                }
                if (whiteCounter == 5 ) {
                    whiteWon = true;
                    return true;
                }
                if(blackCounter == 5){
                    blackWon = true;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean downCheckY() {
        int blackCounter;
        int whiteCounter;
        for (int y = 0; y < sizeY - 4; y++) {
            blackCounter = 0;
            whiteCounter = 0;
            for (int yCopy = y, x = 0; yCopy < sizeY && x < sizeX; yCopy++, x++) {
                Color c = cells[x][yCopy].getColor();
                if (c == null) {
                    blackCounter = 0;
                    whiteCounter = 0;
                } else {
                    if (c.equals(Color.WHITE)) {
                        blackCounter = 0;
                        whiteCounter++;
                    } else {
                        whiteCounter = 0;
                        blackCounter++;
                    }
                }
                if (whiteCounter == 5 ) {
                    whiteWon = true;
                    return true;
                }
                if(blackCounter == 5){
                    blackWon = true;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean upCheckX() {
        int blackCounter;
        int whiteCounter;
        for (int i = sizeX - 1; i > 3; i--) {
            blackCounter = 0;
            whiteCounter = 0;
            for (int xCopy = i, y = 0; xCopy >= 0 && y < sizeY; xCopy--, y++) {
                Color c = cells[xCopy][y].getColor();
                if (c == null) {
                    blackCounter = 0;
                    whiteCounter = 0;
                } else {
                    if (c.equals(Color.WHITE)) {
                        blackCounter = 0;
                        whiteCounter++;
                    } else {
                        whiteCounter = 0;
                        blackCounter++;
                    }
                }
                if (whiteCounter == 5 ) {
                    whiteWon = true;
                    return true;
                }
                if(blackCounter == 5){
                    blackWon = true;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean upCheckY() {
        int blackCounter;
        int whiteCounter;
        for (int y = 0; y < sizeY - 4; y++) {
            blackCounter = 0;
            whiteCounter = 0;
            for (int yCopy = y, x = sizeX - 1; yCopy < sizeY && x >= 0; yCopy++, x--) {
                Color c = cells[x][yCopy].getColor();
                if (c == null) {
                    blackCounter = 0;
                    whiteCounter = 0;
                } else {
                    if (c.equals(Color.WHITE)) {
                        blackCounter = 0;
                        whiteCounter++;
                    } else {
                        whiteCounter = 0;
                        blackCounter++;
                    }
                }
                if (whiteCounter == 5 ) {
                    whiteWon = true;
                    return true;
                }
                if(blackCounter == 5){
                    blackWon = true;
                    return true;
                }
            }
        }
        return false;
    }

    public String printBoard() {
        String s = "";
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                //Piece curr = cells[i][j].getPiece();
                Color col = cells[i][j].getColor();
                if (col != null) {
                    if (col.equals(Color.WHITE))
                        System.out.print(TEXT_BLUE+"W\t"+TEXT_RESET);
                    else System.out.print(TEXT_RED+"B\t"+TEXT_RESET);
                } else {
                    System.out.print(TEXT_WHITE+(i * 15 + j) + "\t"+TEXT_RESET);
                }
            }
            System.out.println();
        }
        return s;
    }


    //todo check
    @Override
    public Board clone(){
        Board b = new Board(sizeX, sizeY);
        for(int i = 0; i < cells.length; i++){
            for (int j = 0; j < cells[i].length; j++) {
                Color c = cells[i][j].getColor();
                if(c != null){
                    b.cells[i][j].addPiece(c);
                }
            }
        }
        return b;
    }

    public int numOfFreePlaces(){
        int counter = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if(cells[i][j].isEmpty()){
                    counter++;
                }
            }
        }
        return counter;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public boolean removePiece(int x, int y){
        whiteWon = false;
        blackWon = false;
        return cells[x][y].removePiece();
    }

}
