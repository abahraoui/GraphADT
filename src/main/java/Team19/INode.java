package Team19;

import java.util.Map;

public interface INode<Key,Weight> {
    Key getKey();
    void addEdge(Key destinationNodeKey,Weight weight);
    Weight getEdgeWeight(Key otherEdgeKey);
    Map<Key, Weight> getEdges();
}
