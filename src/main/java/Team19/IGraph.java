package Team19;

import java.util.ArrayList;

public interface IGraph<NodeInterface,EdgeInterface,NodeKey>  {

    ArrayList<NodeInterface> getNodes();

    abstract void findShortestPath(NodeKey startNodeKey);

    abstract boolean parseInput(ArrayList<String> inputLines);

    boolean importGraph(String input); 

    ArrayList<EdgeInterface> getEdges();
    
}
