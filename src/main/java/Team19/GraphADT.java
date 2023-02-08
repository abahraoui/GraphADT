package Team19;

import java.util.List;

abstract class GraphADT {

    private List<Node> nodes;
    private String startNode;
    private String endNode;
    private int correctLength;

    public GraphADT(){

    }
    public GraphADT(List<Node> node){
          this.nodes = node; // Not sure if this is how we will load the graph tho.

    }
    public GraphADT(List<Node> node,String userStartNode, String userEndNode){
        this.nodes = node;
        this.startNode = userStartNode;
        this.endNode = userEndNode;
    }

    public void setStartNode(String startingNode){
        this.startNode = startingNode;
    }

    public String getStartNode(){
        return startNode;
    }

    public void setEndNode(String endingNode){
        this.endNode = endingNode;
    }

    public String getEndNode(){
        return endNode;
    }

    public boolean checkGuess(int playerGuess){
        boolean success = false; //For future implementation.

        return success;
    }

    public abstract int pathLengthBetweenStartAndEndNode();

    public abstract boolean importGraph(String input);


}
