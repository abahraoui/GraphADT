package Team19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class GraphADT {

    protected List<Node> nodes;
    private String startNode;
    private String endNode;
    private int correctLength;

    public GraphADT() {
        this(new ArrayList<>());
    }

    public GraphADT(List<Node> node) {
        this.nodes = node; // Not sure if this is how we will load the graph tho.
    }

    public GraphADT(List<Node> node, String userStartNode, String userEndNode) {
        this.nodes = node;
        this.startNode = userStartNode;
        this.endNode = userEndNode;
    }

    public void setStartNode(String startingNode) {
        this.startNode = startingNode;
    }

    public String getStartNode() {
        return startNode;
    }

    public void setEndNode(String endingNode) {
        this.endNode = endingNode;
    }

    public String getEndNode() {
        return endNode;
    }

    public boolean checkGuess(int playerGuess) {
        return correctLength == playerGuess;
    }

    public abstract int pathLengthBetweenStartAndEndNode();

    public abstract boolean parseInput(ArrayList<String> inputLines);

    public boolean importGraph(String input) {
        try {
            File myObj = new File(input);
            Scanner myReader = new Scanner(myObj);
            ArrayList<String> data = new ArrayList<>();
            while (myReader.hasNextLine()) {
                data.add(myReader.nextLine());
            }
            myReader.close();
            //System.out.println(data.toString());
            return this.parseInput(data);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return false;
    }

    ;


}
