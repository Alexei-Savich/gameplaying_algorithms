package gameplaying_algorithms;

public class Board {

    private final int sizeX;
    private final int sizeY;
    private Cell[][] cells;

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

    private boolean rowsCheck() {
        int blackCounter;
        int whiteCounter;
        for (int i = 0; i < sizeX; i++) {
            whiteCounter = 0;
            blackCounter = 0;
            for (int j = 0; j < sizeY; j++) {
                Piece curr = cells[i][j].getPiece();
                if (curr == null) {
                    blackCounter = 0;
                    whiteCounter = 0;
                } else {
                    if (curr.getColour().equals(Color.WHITE)) {
                        blackCounter = 0;
                        whiteCounter++;
                    } else {
                        whiteCounter = 0;
                        blackCounter++;
                    }
                }
                if (whiteCounter == 5 || blackCounter == 5) {
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
                Piece curr = cells[j][i].getPiece();
                if (curr == null) {
                    blackCounter = 0;
                    whiteCounter = 0;
                } else {
                    if (curr.getColour().equals(Color.WHITE)) {
                        blackCounter = 0;
                        whiteCounter++;
                    } else {
                        whiteCounter = 0;
                        blackCounter++;
                    }
                }
                if (whiteCounter == 5 || blackCounter == 5) {
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
                Piece curr = cells[k][j].getPiece();
                if (curr == null) {
                    blackCounter = 0;
                    whiteCounter = 0;
                } else {
                    if (curr.getColour().equals(Color.WHITE)) {
                        blackCounter = 0;
                        whiteCounter++;
                    } else {
                        whiteCounter = 0;
                        blackCounter++;
                    }
                }
                if (whiteCounter == 5 || blackCounter == 5) {
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
                Piece curr = cells[x][yCopy].getPiece();
                if (curr == null) {
                    blackCounter = 0;
                    whiteCounter = 0;
                } else {
                    if (curr.getColour().equals(Color.WHITE)) {
                        blackCounter = 0;
                        whiteCounter++;
                    } else {
                        whiteCounter = 0;
                        blackCounter++;
                    }
                }
                if (whiteCounter == 5 || blackCounter == 5) {
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
                Piece curr = cells[xCopy][y].getPiece();
                if (curr == null) {
                    blackCounter = 0;
                    whiteCounter = 0;
                } else {
                    if (curr.getColour().equals(Color.WHITE)) {
                        blackCounter = 0;
                        whiteCounter++;
                    } else {
                        whiteCounter = 0;
                        blackCounter++;
                    }
                }
                if (whiteCounter == 5 || blackCounter == 5) {
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
                Piece curr = cells[x][yCopy].getPiece();
                if (curr == null) {
                    blackCounter = 0;
                    whiteCounter = 0;
                } else {
                    if (curr.getColour().equals(Color.WHITE)) {
                        blackCounter = 0;
                        whiteCounter++;
                    } else {
                        whiteCounter = 0;
                        blackCounter++;
                    }
                }
                if (whiteCounter == 5 || blackCounter == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                Piece curr = cells[i][j].getPiece();
                if (curr != null) {
                    if (curr.getColour().equals(Color.WHITE))
                        System.out.print("W\t");
                    else System.out.print("B\t");
                } else {
                    System.out.print((i * 15 + j) + "\t");
                }
            }
            System.out.println();
        }
        return s;
    }
}
