package gameplaying_algorithms;

import java.util.ArrayList;

public class GameTree{

    Node root;

    public class Node {

        Node[] children;
        Node parent;
        Board value;

        private Node(Board value, int size) {
            this.value = value;
            parent = null;
            children = new Node[size];
        }

        private Node(Board value, Node parent, int size) {
            this.value = value;
            this.parent = parent;
            children = new Node[size];
        }

    }

    public GameTree(){
        root = null;
    }

    public GameTree(Board value, int size){
        root = new Node(value, size);
    }

    public boolean addChildren(Node node, Board[] boards){
        if(node.children != null){
            for (int i = 0; i < boards.length; i++) {
                node.children[i] = new Node(boards[i], node, boards[i].numOfFreePlaces());
            }
            return true;
        }
        return false;
    }

    public Node getRoot() {
        return root;
    }

    public Board[] getChildren(Node n){
        int size = n.children.length;
        Board[] toReturn = new Board[size];
        for (int i = 0; i < size; i++) {
            toReturn[i] = n.children[i].value;
        }
        return toReturn;
    }
}
