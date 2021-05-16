package gameplaying_algorithms;

import org.w3c.dom.Node;

public class MinMax {

    private GameTree tree;
    private Color color;
    private int depth;
    private Board startingBoard;
    private final Cell[][] startingCells;

    public MinMax(Color c, Board b, int depth) {
        color = c;
        tree = new GameTree(b, b.numOfFreePlaces());
        this.depth = depth;
        startingBoard = b;
        this.startingCells = b.getCells();
    }

    public void generateFirstLevel() {
        int startingSize = startingBoard.numOfFreePlaces();
        Board[] boards = new Board[startingSize];
        for (int i = 0; i < boards.length; i++) {
            boards[i] = startingBoard.clone();
        }
        int currX = 0;
        int currY = 0;
        for (int currBoard = 0; currBoard < startingSize; currBoard++) {
            while (!startingCells[currX][currY].isEmpty()) {
                if (currY == 14) {
                    currY = 0;
                    currX++;
                } else {
                    currY++;
                }
            }
            boards[currBoard].nextStep(currX, currY, color);
            if (currY == 14) {
                currY = 0;
                currX++;
            } else {
                currY++;
            }
        }
        tree.addChildren(tree.getRoot(), boards);
    }

    public void generateLevels(int numberLeft, GameTree.Node node, Color c) {
        if (numberLeft == 0) {
            return;
        }
        Board currentBoard = node.value;
        int startingSize = currentBoard.numOfFreePlaces();
        Cell[][] cells = currentBoard.getCells();
        Board[] boards = new Board[startingSize];
        for (int i = 0; i < boards.length; i++) {
            boards[i] = currentBoard.clone();
        }
        int currX = 0;
        int currY = 0;
        for (int currBoard = 0; currBoard < startingSize; currBoard++) {
            while (!cells[currX][currY].isEmpty()) {
                if (currY == 14) {
                    currY = 0;
                    currX++;
                } else {
                    currY++;
                }
            }
            boards[currBoard].nextStep(currX, currY, c);
            if (currY == 14) {
                currY = 0;
                currX++;
            } else {
                currY++;
            }
        }
        Color next;
        if (c.equals(Color.WHITE)) {
            next = Color.BLACK;
        } else {
            next = Color.WHITE;
        }
        tree.addChildren(node, boards);
        for (GameTree.Node curr : node.children) {
            generateLevels(numberLeft - 1, curr, next);
        }
    }

    //todo this method is done only for testing and is not used anywhere
    public void printRange(GameTree.Node node, int start, int end) {
        Board[] boards = tree.getChildren(node);
        for (int i = start; i <= end; i++) {
            System.out.println(boards[i]);
        }
    }

    public GameTree getTree() {
        return tree;
    }
}
