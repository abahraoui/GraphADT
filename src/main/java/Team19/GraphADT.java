package Team19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

abstract class GraphADT {

    protected List<Node> nodes;
    private String startNodeKey;
    private String endNodeKey;
    public double correctLength;
    public ArrayList<String> correctPath;

    public GraphADT() {
        this(new ArrayList<>());
    }

    public GraphADT(List<Node> node) {
        this.nodes = node; // Not sure if this is how we will load the graph tho.
        this.correctPath = new ArrayList<String>();
    }

    public GraphADT(List<Node> node, String userStartNodeKey, String userEndNodeKey) {
        this.nodes = node;
        this.startNodeKey = userStartNodeKey;
        this.endNodeKey = userEndNodeKey;        
    }

    public void setStartNodeKey(String startingNode) {
        this.startNodeKey = startingNode;
        updateCorrectLength();
    }

    public void setRandomStartNode() { 
    	this.startNodeKey = Integer.toString((int) Math.floor(Math.random() *(this.nodes.size() - 0 + 1) + 0));
    	updateCorrectLength();
    }

    public String getStartNodeKey() {
        return startNodeKey;
    }

    public void setEndNodeKey(String endingNode) {
        this.endNodeKey = endingNode;
        updateCorrectLength();
    }

    public void setRandomEndNode() {
    	this.endNodeKey = Integer.toString((int) Math.floor(Math.random() *(this.nodes.size() - 0 + 1) + 0));
    	if(this.endNodeKey == this.startNodeKey) {
    		this.setRandomEndNode();
    	}
    	updateCorrectLength();
    }

    public String getEndNodeKey() {
        return endNodeKey;
    }

    public boolean checkGuess(int playerGuess) {
        return correctLength == playerGuess;
    }

    private void updateCorrectLength() {
        if (this.startNodeKey != null && this.endNodeKey != null)
            this.correctLength = this.findShortestPath();
    }

    public abstract double findShortestPath();

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

    public ArrayList<EdgeDTO> getEdges() {
        ArrayList<EdgeDTO> edges = new ArrayList<>();
        this.nodes.forEach(node -> {
            Map<String, Integer> currentEdges = node.getEdges();
            currentEdges.forEach((currentTo, currentWeight) -> {
                EdgeDTO currentEdge = new EdgeDTO(node.getKey(), currentTo,currentWeight);
                if (!edges.contains(currentEdge)) edges.add(currentEdge);
            });
        });
        return edges;
    }
}
