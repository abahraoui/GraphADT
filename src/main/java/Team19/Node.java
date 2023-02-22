package Team19;

import java.util.HashMap;
import java.util.Map;

public class Node {

    private String key;
    private Map<String, Integer> edges;

    public Node(String key) {
        this.edges = new HashMap<String, Integer>();
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public void addEdge(String destinationNodeKey, int weight) {
        edges.put(destinationNodeKey, weight);
    }

    public int getEdgeWeight(String otherEdgeKey) {
        return edges.get(otherEdgeKey);
    }

    public Map<String, Integer> getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return "Node: key: " + this.getKey() + ", edges: " + getEdges().toString();
    }
}
