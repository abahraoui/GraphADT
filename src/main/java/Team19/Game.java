package Team19;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;


public class Game {
	
	public Game() {
        this.graph = new Graph();
    }
	
	enum Level {
        EASY,
        MEDIUM,
        HARD
    }
	
	final Integer MINPATHLENGTH = 2; // Minimum length of a path
	
	private Level difficulty;
	
	public Graph graph;
	
	private String startNodeKey;
	private String endNodeKey;

	public double correctLength;
	public List<String> correctPath;
	
	public Integer amountOfGuesses = 0;
	public Integer userPlayTime = 0;

public void setStartNodeKey(String startingNode) {
    this.startNodeKey = startingNode;
    updateCorrectLength();
}

public void setRandomStartNode() {
    this.startNodeKey = Integer.toString((int) Math.floor(Math.random() * (graph.getNodes().size() + 1) + 0));
    updateCorrectLength();
}

public String getStartNodeKey() {
    return startNodeKey;
}

public void setEndNodeKey(String endingNode) {
    this.endNodeKey = endingNode;
    this.updateCorrectLength();
}

public void setDifficulty(String diff){
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

}

public void setRandomEndNode() {
    this.findShortestPathBasedOnDiff();
/*        this.endNodeKey = Integer.toString((int) Math.floor(Math.random() *(this.nodes.size()+ 1) + 0));
    if(this.endNodeKey == this.startNodeKey) {
        this.setRandomEndNode();
    }*/
    updateCorrectLength();
    System.out.println(correctPath);
    System.out.println(correctLength);

}

public Integer calculateScore(Integer userPlayTime, Integer amountOfGuesses) {
    userPlayTime = Math.toIntExact(System.nanoTime() * (10 ^ 9)) - userPlayTime;
    switch (this.difficulty) {
        case EASY:
            return (750 * ((10 / amountOfGuesses) / userPlayTime));

        case MEDIUM:
            return (1500 * ((10 / amountOfGuesses) / userPlayTime));

        case HARD:
            return (3000 * ((10 / amountOfGuesses) / userPlayTime));
    }

    return null; // if calculateScore returns null, it means that a difficulty has not been set/passed through
}
public String getEndNodeKey() {
    return endNodeKey;
}

private void updateCorrectLength() {
    if (this.getStartNodeKey() != null && this.getEndNodeKey() != null) {
        graph.findShortestPath(this.getStartNodeKey());
        this.correctLength = graph.distances.get(this.getEndNodeKey());
    }
}

public String checkGuess(int playerGuess) {
    this.amountOfGuesses += 1;
    if (playerGuess > correctLength)
        return "LOWER";
    if (playerGuess < correctLength)
        return "HIGHER";
    return "CORRECT";
}
public void findShortestPathBasedOnDiff() {
    graph.findShortestPath(this.getStartNodeKey());
    Integer max = 2;

    for (ArrayList<String> list : graph.pathsOfAll.values()) {
        if (list.size() > max)
            max = list.size();
    }
    System.out.println("Path with max hops: " + max);

    Integer skillDifference = (max - MINPATHLENGTH) % 3;

    Integer skillDifferenceRandomized = (int) (Math.random() * skillDifference);

    switch (this.difficulty) {
        case EASY:
            this.setEndNodeKey(pickEndNodeBasedOnDiff(MINPATHLENGTH + skillDifferenceRandomized));
            break;
        case MEDIUM:
            this.setEndNodeKey(pickEndNodeBasedOnDiff(skillDifference + MINPATHLENGTH + skillDifferenceRandomized));
            break;
        case HARD:
            this.setEndNodeKey(
                    pickEndNodeBasedOnDiff((skillDifference * 2) + MINPATHLENGTH + skillDifferenceRandomized));
            break;
    }
}

private String pickEndNodeBasedOnDiff(int skillIssue) {
    String randomlyPickedEndNote = null;
    for (Entry<String, ArrayList<String>> e : graph.pathsOfAll.entrySet()) {
        if (e.getValue().size() == skillIssue) {
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


