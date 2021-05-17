package gameplaying_algorithms;

import java.util.ArrayList;
import java.util.Random;

public class MinMax {

    private final Board startingBoard;
    private final Color color;
    private final int depth;

    public MinMax(Board startingBoard, Color color, int depth){
        this.startingBoard = startingBoard.clone();
        this.color = color;
        this.depth = depth;
    }

    public String nextStep(){
        InformationAboutStep[] info = generateFirstLevel();
        evaluateFirstLevel(info);
        int max = Integer.MIN_VALUE;
        for(InformationAboutStep i: info){
            if(i.getValue() > max){
                max = i.getValue();
            }
        }
        ArrayList<InformationAboutStep> best = new ArrayList<>();
        for(InformationAboutStep i: info){
            if(i.getValue() == max){
                best.add(i);
            }
        }
        Random rand = new Random();
        int nextStep = rand.nextInt(best.size());
        int x = best.get(nextStep).getX();
        int y = best.get(nextStep).getY();
        int result = x * 15 + y;
        return result+"";
    }

    public InformationAboutStep[] generateFirstLevel(){
        InformationAboutStep[] info = new InformationAboutStep[startingBoard.numOfFreePlaces()];
        int x = 0;
        int y = 0;
        for (int currElement = 0; currElement < info.length; currElement++) {
            while (!startingBoard.getCells()[x][y].isEmpty()) {
                if (y == 14) {
                    y = 0;
                    x++;
                } else {
                    y++;
                }
            }
            info[currElement] = new InformationAboutStep(x, y, color);
            if(y == 14){
                y = 0;
                x++;
            }
            else{
                y++;
            }
        }
        return info;
    }

    public void evaluateFirstLevel(InformationAboutStep[] info){
        Color newC;
        if(color.equals(Color.WHITE)){
            newC = Color.BLACK;
        }
        else{
            newC = Color.WHITE;
        }
        for(InformationAboutStep i: info){
            evaluate(i, depth - 1, startingBoard, newC, false);
        }
    }

    public int evaluate(InformationAboutStep info, int levelsLeft, Board b, Color curr, boolean isMax){
        if(levelsLeft == 0 || b.isFinished() || b.numOfFreePlaces() == 0){
            info.setValue(b.evaluate(info.getColor()));
            return b.evaluate(info.getColor());
        }
        InformationAboutStep[] infoArr = new InformationAboutStep[startingBoard.numOfFreePlaces()];
        int x = 0;
        int y = 0;
        for (int currElement = 0; currElement < infoArr.length; currElement++) {
            while (!b.getCells()[x][y].isEmpty()) {
                if (y == 14) {
                    y = 0;
                    x++;
                } else {
                    y++;
                }
            }
            infoArr[currElement] = new InformationAboutStep(x, y, curr);

            b.nextStep(x, y, curr);
            Color next = nextColor(curr);
            boolean newIsMax = minMaxChange(isMax);
            evaluate(infoArr[currElement], levelsLeft - 1, b, next, newIsMax);
            b.removePiece(x, y);

            if(y == 14){
                y = 0;
                x++;
            }
            else{
                y++;
            }
        }
        int toChose;
        if(isMax){
            toChose = Integer.MIN_VALUE;
            for(int currElement = 0; currElement < infoArr.length; currElement++){
                if(toChose > infoArr[currElement].getValue()){
                    toChose = infoArr[currElement].getValue();
                }
            }
        }
        else {
            toChose = Integer.MAX_VALUE;
            for(int currElement = 0; currElement < infoArr.length; currElement++){
                if(toChose < infoArr[currElement].getValue()){
                    toChose = infoArr[currElement].getValue();
                }
            }
        }
        info.setValue(toChose);
        return toChose;
    }

    private Color nextColor(Color curr){
        Color next;
        if(curr.equals(Color.WHITE)){
            next = Color.BLACK;
        }
        else{
            next = Color.WHITE;
        }
        return next;
    }

    private boolean minMaxChange(boolean curr){
        boolean next;
        if(curr){
            next = false;
        }
        else{
            next = true;
        }
        return next;
    }

}
