package Team19;

import java.util.List;

public interface IGraph<NodeInterface,EdgeInterface,NodeKey,InputT>  {

    List<NodeInterface> getNodes();

    void findShortestPath(NodeKey startNodeKey);

    boolean importGraph(InputT input); 

    List<EdgeInterface> getEdges();
}
