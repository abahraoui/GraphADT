package Team19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Graph extends GraphADT<String,Double,EdgeDTO,Node> {

    public Graph() {
        super();
    }

    public Graph(ArrayList<Node> nodes){
        super(nodes);

    }

    public Double pathFindingAlgorithim(String startNodeKey,String endNodeKey){
        this.findShortestPath(startNodeKey);
        return distances.get(endNodeKey);
    }

    /**
     *
     * @param startNodeKey given the starting node, this function finds the shortest paths to all other nodes using the
     *                     dijkstra algorithm
     */
    public void findShortestPath(String startNodeKey) {
        if (startNodeKey == null)
            throw new NullPointerException("No start node is set");

        Map<String, String> shortestPrevNode = new HashMap<>();
        Map<String, Boolean> sptSet = new HashMap<>();

        this.getNodes().forEach((node) -> {
            this.distances.put(node.getKey(),
                    node.getKey().equals(startNodeKey) ? 0 : Double.POSITIVE_INFINITY);
            sptSet.put(node.getKey(), false);
        });

        for (int count = 0; count < this.getNodes().size(); count++) {
            String minDistanceEntryKey = minDistanceEntry(this.distances, sptSet);
            Node outerNode = this.getNodes().stream().filter(n -> n.getKey().equals(minDistanceEntryKey))
                    .findFirst().orElse(null);
            sptSet.put(outerNode.getKey(), true);

            this.getNodes().forEach(innerNode -> {
                Double edgeWeight = outerNode.getEdges().get(innerNode.getKey());
                Double outerNodeDistance = this.distances.get(outerNode.getKey());
                Double innerNodeDistance = this.distances.get(innerNode.getKey());
                if (!sptSet.get(innerNode.getKey()) &&
                        (edgeWeight != null && edgeWeight != 0) &&
                        outerNodeDistance != Double.POSITIVE_INFINITY &&
                        (outerNodeDistance + edgeWeight < innerNodeDistance)) {
                    this.distances.put(innerNode.getKey(), outerNodeDistance + edgeWeight);
                    shortestPrevNode.put(innerNode.getKey(), outerNode.getKey());
                }
            });
        }

        Map<String, ArrayList<String>> pathOfAll = new HashMap<>();
        this.getNodes().forEach(node -> {
            if (node.getKey().equals(startNodeKey)) {
                pathOfAll.put(node.getKey(),
                        new ArrayList<String>() {
                            {
                                add(node.getKey());
                            }
                        });
                return;
            }
            ArrayList<String> pathToThisNode = new ArrayList<>();
            pathToThisNode.add(node.getKey());
            String tempEnd = shortestPrevNode.get(node.getKey());
            if (tempEnd != null) {
                while (!(tempEnd.equals(startNodeKey))) {
                    pathToThisNode.add(tempEnd);
                    tempEnd = shortestPrevNode.get(tempEnd);
                }
            }
            pathToThisNode.add(tempEnd);
            pathOfAll.put(node.getKey(), pathToThisNode);
        });
        System.out.println();
        System.out.println();
        System.out.print("pathOfAll ");
        System.out.println(pathOfAll);
        System.out.println();
        System.out.println();

        this.pathsOfAll = pathOfAll;
    }


    /**
     *
     * @param distances this is a map to all the nodes from our starting node
     * @param sptSet a boolean list, which becomes true if we have found the shortest path to that node
     * @return we return the key of the node that is the closest to our currently examined node?
     */
    private String minDistanceEntry(Map<String, Double> distances, Map<String, Boolean> sptSet) {
        Entry<String, Double> min = null;
        for (Entry<String, Double> entry : distances.entrySet())
            if ((min == null || min.getValue() > entry.getValue()) && !sptSet.get(entry.getKey()))
                min = entry;
        return min == null ? null : min.getKey();
    }



    public boolean parseInput(ArrayList<String> lines) {
        final String parseRegex = "(\\d*) (\\d*) \\{'weight': (\\d*)}";
        Pattern pattern = Pattern.compile(parseRegex);
        try {
            lines.forEach((line) -> {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    String thisNodeKey = matcher.group(1);
                    String thatNodeKey = matcher.group(2);
                    Double edgeWeight = (double) Integer.parseInt(matcher.group(3));

                    Node node = null;
                    Node node2 = null;

                    for (Node currentNode : this.getNodes()) {
                        if (currentNode.getKey().equals(thisNodeKey)) {
                            node = currentNode;
                            node.addEdge(thatNodeKey, edgeWeight);
                        }
                        if (currentNode.getKey().equals(thatNodeKey)) {
                            node2 = currentNode;
                            node2.addEdge(thisNodeKey, edgeWeight);
                        }
                    }

                    if (node == null) {
                        node = new Node(thisNodeKey);
                        addNode(node);
                        node.addEdge(thatNodeKey, edgeWeight);
                    }
                    if (node2 == null) {
                        node2 = new Node(thatNodeKey);
                        addNode(node2);
                        node2.addEdge(thisNodeKey, edgeWeight);
                    }

                }
            });
        } catch (NullPointerException e) {
            throw new NullPointerException("An error occured because of your input");
        }

        return true;
    }

    public void updateEdges() {
        this.nodes.forEach(node -> {
            Map<String, Double> currentEdges = node.getEdges();
            currentEdges.forEach((currentTo, currentWeight) -> {
                EdgeDTO currentEdge = new EdgeDTO(node.getKey(), currentTo, currentWeight);
                if (!edges.contains(currentEdge)) edges.add(currentEdge);
            });
        });
    }

    public ArrayList<EdgeDTO> getEdges() {
        updateEdges();
        return edges;
    }

}
