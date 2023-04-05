package Team19;

import java.util.ArrayList;
import java.util.Map.Entry;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;

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
        this.userPlayTime = System.currentTimeMillis();
        response.addProperty("startNodeKey", this.getStartNodeKey());
        response.addProperty("endNodeKey",this.getEndNodeKey());
        response.add("edges", edgesJson);
        return new Gson().toJson(response);
    }


    public String generateStartNodeBasedOnDifficulty() {
        return Integer.toString((int) Math.floor(Math.random() * (graph.getNodes().size() + 1) + 0));
    }

    public String generateEndNodeBasedOnDifficulty() {
        return this.pathLengthByDiff();
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

    /**
     * userPlayTime we initialise this parameter with the time the user starts playing the game,
     * then we substract the time the user has found the correct path length.
     * @return using userPlayTime and amountOfGuesses we calculate the score, giving different initial scores based on
     * the difficulty.
     */
    public Long calculateScore() {
        Long timetaken = System.currentTimeMillis() - userPlayTime;
        switch (this.difficulty) {
            case HARD:
                return ((3000 * (10 / this.amountOfGuesses)) / (timetaken/1000));
            case MEDIUM:
                return ((1500 * (10 / this.amountOfGuesses)) / (timetaken/1000));
            default: //custom difficulty is rewarded the same as easy
                return ((750 * (10 / this.amountOfGuesses)) / (timetaken/1000));
        }
    }

    public void updateCorrectLength() {
        if (this.getStartNodeKey() != null && this.getEndNodeKey() != null) {
            this.correctLength = graph.pathFindingAlgorithim(this.getStartNodeKey(),this.getEndNodeKey());
            this.correctPath = graph.pathsOfAll.get(this.getEndNodeKey());
        }
    }

    public String checkGuess(Double playerGuess) {
        this.amountOfGuesses += 1;
        boolean isCorrect = playerGuess.equals(correctLength);

        JsonObject response = new JsonObject();
        response.add("feedback", new JsonPrimitive(isCorrect ? "CORRECT" : (playerGuess > correctLength ? "LOWER" : "HIGHER")));
        response.add("isCorrect", new JsonPrimitive(isCorrect));
        if (isCorrect) {
            JsonArray pathJsonArray = new JsonArray();
            this.correctPath.forEach(node -> pathJsonArray.add(node));
            response.add("path", pathJsonArray);
            response.add("score", new JsonPrimitive(isCorrect ? this.calculateScore() : -1));
            response.add("tries", new JsonPrimitive(this.amountOfGuesses));

            response.add("seconds", new JsonPrimitive((int) Math.floor((System.currentTimeMillis() - this.userPlayTime) / 1000.0)));
        }
        return new Gson().toJson(response);
    }

    /**
     *
     * @return Picks a random end node within the range of our difficulty calculation
     */
    public String pathLengthByDiff() {
        graph.findShortestPath(this.getStartNodeKey());
        Integer max = 2;

        for (ArrayList<String> list : graph.pathsOfAll.values()) {
            if (list.size() > max)
                max = list.size();
        }

        Integer difficultyIncrease = (max - MINPATHLENGTH) % 3;

        Integer difficultyIncreaseRandomized = (int) (Math.random() * difficultyIncrease);

        difficultyIncreaseRandomized += MINPATHLENGTH;

        switch (this.difficulty) {
            case HARD:
                this.difficultyFactor = (difficultyIncrease * 2) + difficultyIncreaseRandomized;
                break;
            case MEDIUM:
                this.difficultyFactor = (difficultyIncrease * 1) + difficultyIncreaseRandomized;
                break;
            default:
                this.difficultyFactor = (difficultyIncrease * 0) + difficultyIncreaseRandomized;
                break;
        }
        return pickEndNodeBasedOnDiff();
    }


    /**
     *
     * difficultyFactor is a randomized number given by the pathLengthByDiff() function.
     *
     * @return we return the number of the pick node, which has a path length equal to difficultyFactor.
     *
     */
    public String pickEndNodeBasedOnDiff() {
        String randomlyPickedEndNote = null;
        for (Entry<String, ArrayList<String>> e : graph.pathsOfAll.entrySet()) {
            if (e.getValue().size() == this.difficultyFactor) {
                if (randomlyPickedEndNote == null) {
                    randomlyPickedEndNote = e.getKey();
                } else {
                    if (Math.random() > 0.5) {
                        randomlyPickedEndNote = e.getKey();
                    }
                }
            }
        }
        return randomlyPickedEndNote;
    }
}


