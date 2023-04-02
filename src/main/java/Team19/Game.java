package Team19;

import java.util.ArrayList;
import java.util.Map.Entry;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.Gson;

public class Game extends GameADT<String,Graph,String,String,Double,Long> {
	
	public Game() {
        this.graph = new Graph();
    }
	
	final Integer MINPATHLENGTH = 2; // Minimum length of a path
	


    public String createGraph(String start_node,  String end_node,  String difficulty) {
        String diff = (difficulty != null) ? difficulty.toLowerCase() : "easy";
        this.setDifficulty(diff);

        String startNode = (start_node != null) ? start_node : this.generateStartNodeBasedOnDifficulty();
        this.setStartNodeKey(startNode);
        String endNode = (end_node != null) ? end_node : this.generateEndNodeBasedOnDifficulty();

        return createGraph(startNode, endNode);
    }

    public String createGraph(String start_node, String end_node) {
        this.setStartNodeKey(start_node);
        this.setEndNodeKey(end_node);
        amountOfGuesses = 0;
        JsonArray edgesJson = new JsonArray();
        ArrayList<EdgeDTO> edges = this.graph.getEdges();
        edges.forEach(edge -> {
            JsonObject edgeJson = new JsonObject();
            edgeJson.addProperty("from", edge.from);
            edgeJson.addProperty("to", edge.to);
            edgeJson.addProperty("weight", edge.weight);
            edgesJson.add(edgeJson);
        });
        JsonObject response = new JsonObject();
        this.userPlayTime = System.nanoTime()/(10^9);
        response.addProperty("startNodeKey", this.getStartNodeKey());
        response.addProperty("endNodeKey",this.getEndNodeKey());
        response.add("edges", edgesJson);
        return new Gson().toJson(response);
    }


    public String generateStartNodeBasedOnDifficulty() {
        return Integer.toString((int) Math.floor(Math.random() * (graph.getNodes().size() + 1) + 0));
    }

    public String generateEndNodeBasedOnDifficulty() {
        return this.findShortestPathBasedOnDiff();
    }

    public void setDifficulty(String diff){
        switch (diff) {
            case "hard":
                difficulty = Level.HARD;
                break;
            case "medium":
                difficulty = Level.MEDIUM;
                break;
            default:
                difficulty = Level.EASY;
                break;
        }
    }

    //TODO comment this please
    /**
     *
     * @return
     */
    public Long calculateScore() {
        userPlayTime = System.nanoTime() / (10 ^ 9) - userPlayTime;
        switch (this.difficulty) {
            case HARD:
                return (3000 * ((10 / this.amountOfGuesses) / userPlayTime));    
            case MEDIUM:
                return (1500 * ((10 / this.amountOfGuesses) / userPlayTime));
            default: //custom difficulty is rewarded the same as easy
                return (750 * ((10 / this.amountOfGuesses) / userPlayTime));
        }
    }

    public void updateCorrectLength() {
        if (this.getStartNodeKey() != null && this.getEndNodeKey() != null) {
            graph.findShortestPath(this.getStartNodeKey());
            this.correctLength = graph.distances.get(this.getEndNodeKey());
        }
    }

    public String checkGuess(Double playerGuess) {
        this.amountOfGuesses += 1;
        if (playerGuess > correctLength)
            return "LOWER";
        if (playerGuess < correctLength)
            return "HIGHER";
        // Integer score = this.calculateScore(Math.toIntExact(System.nanoTime() * (10 ^ 9)),this.amountOfGuesses);
        return "CORRECT your score was " + this.calculateScore();
    }

    //TODO comment this please

    /**
     *
     * @return
     */
    public String findShortestPathBasedOnDiff() {
        graph.findShortestPath(this.getStartNodeKey());
        Integer max = 2;

        for (ArrayList<String> list : graph.pathsOfAll.values()) {
            if (list.size() > max)
                max = list.size();
        }
        System.out.println("Path with max hops: " + max);

        Integer difficultyIncrease = (max - MINPATHLENGTH) % 3;

        Integer difficultyIncreaseRandomized = (int) (Math.random() * difficultyIncrease);

        switch (this.difficulty) {
            case HARD:
                return pickEndNodeBasedOnDiff((difficultyIncrease * 2) + MINPATHLENGTH + difficultyIncreaseRandomized);
                
            case MEDIUM:
                return pickEndNodeBasedOnDiff((difficultyIncrease * 1) + MINPATHLENGTH + difficultyIncreaseRandomized);
                
            default:
                return pickEndNodeBasedOnDiff((difficultyIncrease * 0) + MINPATHLENGTH + difficultyIncreaseRandomized);
                
        }
    }

    //TODO comment this please

    /**
     *
     * @param PathLengthBasedOnDiff
     * @return
     */
    public String pickEndNodeBasedOnDiff(int PathLengthBasedOnDiff) {
        String randomlyPickedEndNote = null;
        for (Entry<String, ArrayList<String>> e : graph.pathsOfAll.entrySet()) {
            if (e.getValue().size() == PathLengthBasedOnDiff) {
                if (randomlyPickedEndNote == null) {
                    randomlyPickedEndNote = e.getKey();
                } else {
                    if (Math.random() > 0.5) {
                        randomlyPickedEndNote = e.getKey();
                    }
                }
            }
        }
        this.correctPath = graph.pathsOfAll.get(randomlyPickedEndNote);
        return randomlyPickedEndNote;
    }
}


