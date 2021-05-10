package gameplaying_algorithms;

public class Piece {

    private final int x, y;
    private final Color colour;

    public Piece(int x, int y, Color colour) {
        this.x = x;
        this.y = y;
        this.colour = colour;
    }

    public Color getColour() {
        return colour;
    }
}
