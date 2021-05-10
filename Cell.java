package gameplaying_algorithms;

public class Cell {

    private final int x;
    private final int y;
    private Piece piece;
    private boolean isEmpty = true;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        piece = null;
    }

    public boolean addPiece(Color c) {
        if (isEmpty) {
            piece = new Piece(x, y, c);
            isEmpty = false;
            return true;
        }
        return false;
    }


    public boolean isEmpty() {
        return isEmpty;
    }

    public Piece getPiece() {
        return piece;
    }
}
