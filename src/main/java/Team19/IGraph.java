package Team19;

import java.util.List;

public interface IGraph<NodeInterface,EdgeInterface,NodeKey,PathT,InputT>  {

    List<NodeInterface> getNodes();

    abstract PathT pathFindingAlgorithim(NodeKey startNodeKey,NodeKey endNodeKey);
    
    void addNode(NodeInterface node);

    boolean importGraph(InputT input); 

    List<EdgeInterface> getEdges();
}
