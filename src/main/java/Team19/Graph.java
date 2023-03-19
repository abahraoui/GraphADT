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

    static final Integer MINPATHLENGTH = 2; // Minimum length of a path


    public Map<String, List<String>> findShortestPath() {
        if (this.getStartNodeKey() == null)
            throw new Error("No start node is set");

        Map<String, String> shortestPrevNode = new HashMap<>();
        Map<String, Boolean> sptSet = new HashMap<>();


        this.nodes.forEach((node) -> {
            this.distances.put(node.getKey(),
                    node.getKey().equals(this.getStartNodeKey()) ? 0 : Double.POSITIVE_INFINITY);
            sptSet.put(node.getKey(), false);
        });

        for (int count = 0; count < this.nodes.size(); count++) {
            String minDistanceEntryKey = minDistanceEntry(this.distances, sptSet);
            Node outerNode = this.nodes.stream().filter(n -> n.getKey().equals(minDistanceEntryKey))
                    .findFirst().orElse(null);
            sptSet.put(outerNode.getKey(), true);

            this.nodes.forEach(innerNode -> {
                Integer edgeWeight = outerNode.getEdges().get(innerNode.getKey());
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

        Map<String, List<String>> pathOfAll = new HashMap<>();
        this.nodes.forEach(node -> {
            if (node.getKey().equals(getStartNodeKey())) {
                pathOfAll.put(node.getKey(), Collections.singletonList(node.getKey()));
                return;
            }
            ArrayList<String> pathToThisNode = new ArrayList<>();
            pathToThisNode.add(node.getKey());
            String tempEnd = shortestPrevNode.get(node.getKey());
            if (tempEnd != null) {
                while (!(tempEnd.equals(this.getStartNodeKey()))) {
                    pathToThisNode.add(tempEnd);
                    tempEnd = shortestPrevNode.get(tempEnd);
                }
            }
            pathToThisNode.add(tempEnd);
            pathOfAll.put(node.getKey(), pathToThisNode);
        });


        return pathOfAll;
    }

    public void findShortestPathBasedOnDiff(Level Difficulty) {
        Map<String, List<String>> pathOfAll = findShortestPath();
        Integer max = 2;

        for (List<String> list : pathOfAll.values()){
            if (list.size() > max)
                max = list.size();
        }
        System.out.println(max);

        Integer skillDifference = (max-MINPATHLENGTH) % 3;

        Integer skillDifferenceRandomized = (int)(Math.random() * skillDifference);

        switch (Difficulty) {
            case EASY:
                setEndNodeKey(pickEndNodeBasedOnDiff(MINPATHLENGTH+skillDifferenceRandomized, pathOfAll));

                break;
            case MEDIUM:
                setEndNodeKey(pickEndNodeBasedOnDiff(skillDifference+MINPATHLENGTH+skillDifferenceRandomized, pathOfAll));
                break;

            case HARD:
                setEndNodeKey(pickEndNodeBasedOnDiff((skillDifference*2)+MINPATHLENGTH+skillDifferenceRandomized, pathOfAll));
                break;
        }
    }

    private String pickEndNodeBasedOnDiff(Integer skillIssue, Map<String, List<String>> bigMap){
        String randomlyPickedEndNote = null;
        for (Entry<String, List<String>> e : bigMap.entrySet()){
            int len = e.getValue().size();
            if (len == (skillIssue)){
                if(randomlyPickedEndNote == null){
                    randomlyPickedEndNote = e.getKey();
                }else{
                    if(Math.random() > 0.5){
                        randomlyPickedEndNote = e.getKey();
                    }
                }
            }
        }

        this.correctPath = bigMap.get(randomlyPickedEndNote);

        return randomlyPickedEndNote;
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
