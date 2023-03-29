package Team19;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Graph extends GraphADT {
    @Override
    public double findShortestPath() {
        if (this.getStartNodeKey() == null)
            throw new Error("No start node is set");
        if (this.getEndNodeKey() == null)
            throw new Error("No start node is set");

        Map<String, Double> distances = new HashMap<>();
        Map<String, Boolean> sptSet = new HashMap<>();
        Map<String, String> shortestPrevNode = new HashMap<>();

        this.nodes.forEach((node) -> {
            distances.put(node.getKey(),
                    node.getKey().equals(this.getStartNodeKey()) ? 0 : Double.POSITIVE_INFINITY);
            sptSet.put(node.getKey(), false);
        });

        for (int count = 0; count < this.nodes.size(); count++) {
            String minDistanceEntryKey = minDistanceEntry(distances, sptSet);
            Node outerNode = this.nodes.stream().filter(n -> n.getKey().equals(minDistanceEntryKey))
                    .findFirst().orElse(null);
            sptSet.put(outerNode.getKey(), true);

            this.nodes.forEach(innerNode -> {
                Integer edgeWeight = outerNode.getEdges().get(innerNode.getKey());
                Double outerNodeDistance = distances.get(outerNode.getKey());
                Double innerNodeDistance = distances.get(innerNode.getKey());
                if (!sptSet.get(innerNode.getKey()) &&
                        (edgeWeight != null && edgeWeight != 0) &&
                        outerNodeDistance != Double.POSITIVE_INFINITY &&
                        (outerNodeDistance + edgeWeight < innerNodeDistance)) {
                    distances.put(innerNode.getKey(), outerNodeDistance + edgeWeight);
                    shortestPrevNode.put(innerNode.getKey(), outerNode.getKey());
                }
            });
        }
        System.out.print("shortestPrevNode ");
        System.out.println(shortestPrevNode);

        Map<String, List<String>> pathOfAll = new HashMap<>();
        this.nodes.forEach(node -> {
            if (node.getKey().equals("0")) {
                pathOfAll.put(node.getKey(), Collections.singletonList(node.getKey()));
                return;
            }
            ArrayList<String> pathToThisNode = new ArrayList<>();
            pathToThisNode.add(node.getKey());
            String tempEnd = shortestPrevNode.get(node.getKey());
            while (!(tempEnd.equals(this.getStartNodeKey()))) {
                pathToThisNode.add(tempEnd);
                tempEnd = shortestPrevNode.get(tempEnd);
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

        String tempEnd = shortestPrevNode.get(this.getEndNodeKey());
        this.correctPath.add(this.getEndNodeKey());
        while (!(tempEnd.equals(this.getStartNodeKey()))) {
            this.correctPath.add(tempEnd);
            tempEnd = shortestPrevNode.get(tempEnd);
        }
        this.correctPath.add(tempEnd);
        System.out.println(this.correctPath);
        return distances.get(this.getEndNodeKey());
    }

    private String minDistanceEntry(Map<String, Double> distances, Map<String, Boolean> sptSet) {
        Entry<String, Double> min = null;
        for (Entry<String, Double> entry : distances.entrySet())
            if ((min == null || min.getValue() > entry.getValue()) && !sptSet.get(entry.getKey()))
                min = entry;
        return min == null ? null : min.getKey();
    }

    // 0 36 {'weight': 9}
    @Override
    public boolean parseInput(ArrayList<String> lines) {
        final String parseRegex = "(\\d*) (\\d*) \\{'weight': (\\d*)}";
        Pattern pattern = Pattern.compile(parseRegex);
        try {
            lines.forEach((line) -> {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    String thisNodeKey = matcher.group(1);
                    String thatNodeKey = matcher.group(2);
                    int edgeWeight = Integer.parseInt(matcher.group(3));

                    Node node = null;
                    Node node2 = null;

                    for (Node currentNode : this.nodes) {
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
                        this.nodes.add(node);
                        node.addEdge(thatNodeKey, edgeWeight);
                    }
                    if (node2 == null) {
                        node2 = new Node(thatNodeKey);
                        this.nodes.add(node2);
                        node2.addEdge(thisNodeKey, edgeWeight);
                    }

                }
            });
        } catch (Error error) {
            System.err.println(error.getCause());
            return false;
        }

        return true;
    }

}
