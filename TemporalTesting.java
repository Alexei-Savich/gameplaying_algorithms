package gameplaying_algorithms;

public class TemporalTesting {

    public static void main(String[] args){
        Gomoku game = new Gomoku();
        System.out.println(game.getBoard());
        MinMax mm = new MinMax(Color.WHITE, game.getBoard(), 3);
        mm.generateFirstLevel();
        for(GameTree.Node curr: mm.getTree().root.children)
        mm.generateLevels(1, curr, Color.BLACK);
        mm.printRange(mm.getTree().getRoot().children[55], 0, 223);
    }

}
