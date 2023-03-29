package Team19;

import java.util.Map;

public interface NodeI<Key,Weight> {
    public Key getKey();
    public void addEdge(Key destinationNodeKey,Weight weight);
    public Weight getEdgeWeight(Key otherEdgeKey);
    public Map<Key, Weight> getEdges();
}
