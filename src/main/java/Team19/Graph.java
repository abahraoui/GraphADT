package Team19;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Graph extends GraphADT {
    @Override
    public int pathLengthBetweenStartAndEndNode() {
        return 0;
    }

    //0 36 {'weight': 9}
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
                    System.out.println(line + " " + thisNodeKey + " " + thatNodeKey + " " + edgeWeight);

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
            System.out.println(this.nodes.size());
            System.out.println(this.nodes.get(29));
        } catch (Error bingchilling) {
            System.err.println(bingchilling.getCause());
            return false;
        }

        return true;
    }

}
