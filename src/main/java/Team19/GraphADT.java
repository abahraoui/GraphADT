package Team19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

abstract class GraphADT {
    

    private ArrayList<Node> nodes;

    protected Map<String, Double> distances = new HashMap<>();
    protected Map<String, ArrayList<String>> pathsOfAll;


    public GraphADT() {
        this(new ArrayList<>());

    }
    
    public GraphADT(ArrayList<Node> node) {
        this.nodes = node;
    }

    public ArrayList<Node> getNodes(){
        return this.nodes;
    }

    public abstract void findShortestPath(String startNodeKey);

    public abstract boolean parseInput(ArrayList<String> inputLines);

    public boolean importGraph(String input) {
        try {
            File myObj = new File(input);
            Scanner myReader = new Scanner(myObj);
            ArrayList<String> data = new ArrayList<>();
            while (myReader.hasNextLine()) {
                data.add(myReader.nextLine());
            }
            myReader.close();
            //System.out.println(data.toString());
            return this.parseInput(data);
        } catch (FileNotFoundException e) {
            throw new NullPointerException("File not found.");
        }
    }

    public ArrayList<EdgeDTO> getEdges() {
        ArrayList<EdgeDTO> edges = new ArrayList<>();
        this.nodes.forEach(node -> {
            Map<String, Integer> currentEdges = node.getEdges();
            currentEdges.forEach((currentTo, currentWeight) -> {
                EdgeDTO currentEdge = new EdgeDTO(node.getKey(), currentTo, currentWeight);
                if (!edges.contains(currentEdge)) edges.add(currentEdge);
            });
        });
        return edges;
    }
}

