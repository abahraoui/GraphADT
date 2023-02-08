package Team19;

import java.util.Map;

public class Node {

    private String key;
    private Map<String,Integer> edges;

    public Node(String key){
        this.key = key;
    }

    public String getKey(){
        return this.key;
    }

    public void addEdge(String node,int weight){
          edges.put(node,weight);
    }

    public int getEdgeWeight(String key){
        return edges.get(key);
    }

    public Map<String,Integer> getEdges(){
        return edges;
    }



}
