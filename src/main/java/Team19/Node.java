package Team19;

import java.util.HashMap;
import java.util.Map;

public class Node implements INode<String,Double> {

    private String key;
    private Map<String, Double> edges;

    public Node(String key) {
        this.edges = new HashMap<String, Double>();
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public void addEdge(String destinationNodeKey, Double weight) {
        edges.put(destinationNodeKey, weight);
    }

    public Double getEdgeWeight(String otherEdgeKey) {
        return edges.get(otherEdgeKey);
    }

    public Map<String, Double> getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return "Node: key: " + this.getKey() + ", edges: " + getEdges().toString();
    }
}
