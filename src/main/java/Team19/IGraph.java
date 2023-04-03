package Team19;

import java.util.ArrayList;

public interface IGraph<NodeInterface,EdgeInterface,NodeKey,PathT>  {

    ArrayList<NodeInterface> getNodes();

    abstract PathT pathFindingAlgorithim(NodeKey startNodeKey,NodeKey endNodeKey);
    
    void addNode(NodeInterface node);

    abstract boolean parseInput(ArrayList<String> inputLines);

    boolean importGraph(String input); 

    ArrayList<EdgeInterface> getEdges();
    
}
