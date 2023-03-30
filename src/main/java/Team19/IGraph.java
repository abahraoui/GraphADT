package Team19;

import java.util.ArrayList;

public interface IGraph<NodeInterface,EdgeInterface,NodeKey>  {

    public ArrayList<NodeInterface> getNodes();

    public abstract void findShortestPath(NodeKey startNodeKey);

    public abstract boolean parseInput(ArrayList<String> inputLines);

    public boolean importGraph(String input); 

    public ArrayList<EdgeInterface> getEdges();
    
}
