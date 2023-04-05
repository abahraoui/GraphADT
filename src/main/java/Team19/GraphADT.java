package Team19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

abstract class GraphADT<NodeKey,NodeWeight,EdgeInterface,NodeInterface> implements IGraph<NodeInterface,EdgeInterface,NodeKey,NodeWeight,String> {

    protected ArrayList<NodeInterface> nodes;
    protected ArrayList<EdgeInterface> edges;

    protected Map<NodeKey, NodeWeight> distances = new HashMap<>();
    protected Map<NodeKey, ArrayList<NodeKey>> pathsOfAll;


    public GraphADT() {
        this(new ArrayList<>());

    }

    public GraphADT(ArrayList<NodeInterface> node) {
        this.nodes = node;
        this.edges = new ArrayList<>();
    }

    public ArrayList<NodeInterface> getNodes(){
        return this.nodes;
    }

    public void addNode(NodeInterface node){
        this.getNodes().add(node);
    }

    public abstract NodeWeight pathFindingAlgorithim(NodeKey startNodeKey,NodeKey endNodeKey);

    public abstract void findShortestPath(NodeKey startNodeKey);

    public abstract boolean parseInput(ArrayList<String> inputLines);

    public abstract void updateEdges();

    public boolean importGraph(String input) {
        try {
            File myObj = new File(input);
            Scanner myReader = new Scanner(myObj);
            ArrayList<String> data = new ArrayList<>();
            while (myReader.hasNextLine()) {
                data.add(myReader.nextLine());
            }
            myReader.close();
            return this.parseInput(data);
        } catch (FileNotFoundException e) {
            throw new NullPointerException("File not found.");
        }
    }
    public ArrayList<EdgeInterface> getEdges() {
        this.updateEdges();
        return edges;
    }
}

