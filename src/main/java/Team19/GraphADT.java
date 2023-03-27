package Team19;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

abstract class GraphADT {
    enum Level {
        EASY,
        MEDIUM,
        HARD
    }

    protected List<Node> nodes;
    private String startNodeKey;

    public Level difficulty;

    private String endNodeKey;
    public double correctLength;
    public List<String> correctPath;

    protected Map<String, Double> distances;


    public GraphADT() {
        this(new ArrayList<>());
    }

    public GraphADT(List<Node> node) {
        this.nodes = node; // Not sure if this is how we will load the graph tho.
        this.correctPath = new ArrayList<String>();
        this.distances = new HashMap<>();
        this.difficulty = Level.EASY;
    }

    public GraphADT(List<Node> node, String userStartNodeKey, String userEndNodeKey) {
        this.nodes = node;
        this.startNodeKey = userStartNodeKey;
        this.endNodeKey = userEndNodeKey;
    }

    public void setStartNodeKey(String startingNode) {
        this.startNodeKey = startingNode;
        updateCorrectLength();
    }

    public void setRandomStartNode() {
        this.startNodeKey = Integer.toString((int) Math.floor(Math.random() * (this.nodes.size() + 1) + 0));
        updateCorrectLength();
    }

    public String getStartNodeKey() {
        return startNodeKey;
    }

    public void setEndNodeKey(String endingNode) {
        this.endNodeKey = endingNode;
        updateCorrectLength();
    }

    public void setRandomEndNode(String diff) {
        switch (diff) {
            case "easy":
                difficulty = Level.EASY;
                break;
            case "medium":
                difficulty = Level.MEDIUM;
                break;
            default:
                difficulty = Level.HARD;
                break;
        }


        findShortestPathBasedOnDiff(difficulty);
/*        this.endNodeKey = Integer.toString((int) Math.floor(Math.random() *(this.nodes.size()+ 1) + 0));
        if(this.endNodeKey == this.startNodeKey) {
            this.setRandomEndNode();
        }*/
        updateCorrectLength();
        System.out.println(correctPath);
        System.out.println(correctLength);

    }

    public String getEndNodeKey() {
        return endNodeKey;
    }

    public boolean checkGuess(int playerGuess) {
        return correctLength == playerGuess;
    }

    private void updateCorrectLength() {
        if (this.startNodeKey != null && this.endNodeKey != null)
            this.correctLength = this.distances.get(this.getEndNodeKey());
    }

    public abstract void findShortestPathBasedOnDiff(Level difficulty);


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
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return false;
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
