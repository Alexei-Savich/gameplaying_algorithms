package gameplaying_algorithms;

public class InformationAboutStep {

    private final int x;
    private final int y;
    private Integer value;
    private final Color color;

    public InformationAboutStep(int x, int y, Color color){
        this.x = x;
        this.y = y;
        this.color = color;
        value = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "("+x+", "+y+"): "+value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Color getColor() {
        return color;
    }

}
