package gameplaying_algorithms;

public class Cell {

    private final int x;
    private final int y;
    private Color color = null;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean addPiece(Color c) {
        if (isEmpty()) {
            color = c;
            return true;
        }
        return false;
    }

    public boolean removePiece(){
        if(!isEmpty()){
            color = null;
            return true;
        }
        return false;
    }


    public boolean isEmpty() {
        return color == null;
    }

    public Color getColor() {
        return  color;
    }
}