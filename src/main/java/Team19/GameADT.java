package Team19;

import java.util.List;

public abstract class GameADT<NodeKey,GraphT,FeedbackT,DifficultyInputT,GuessT,ScoreT> implements IGameBase<NodeKey,FeedbackT,GuessT>,IGameDifficulty<NodeKey,DifficultyInputT,ScoreT> {



    enum Level {
        HARD,
        MEDIUM,
        EASY
    }

	protected Integer MINPATHLENGTH; // Minimum length of a path

    protected Level difficulty;
    protected Integer difficultyFactor;

    public GraphT graph;

    protected NodeKey startNodeKey;
    protected NodeKey endNodeKey;

    public GuessT correctLength;
    protected  List<NodeKey> correctPath;

    public Integer amountOfGuesses = 0;
    public long userPlayTime = 0;


    public abstract String createGraph(NodeKey start_node,  NodeKey end_node,  DifficultyInputT difficulty);

    public void setStartNodeKey(NodeKey startingNode) {
        this.startNodeKey = startingNode;
        updateCorrectLength();
    }

    public void setEndNodeKey(NodeKey endingNode) {
        this.endNodeKey = endingNode;
        this.updateCorrectLength();
    }

    public NodeKey getStartNodeKey() {
        return startNodeKey;
    }

    public NodeKey getEndNodeKey() {
        return endNodeKey;
    }

    public GuessT getDistanceBetweenNodes(){
        return correctLength;
    }

    public abstract void setDifficulty(DifficultyInputT diff);

    public abstract NodeKey generateStartNodeBasedOnDifficulty();

    public abstract NodeKey generateEndNodeBasedOnDifficulty();

    public abstract ScoreT calculateScore();

    public abstract void updateCorrectLength();

    public abstract FeedbackT checkGuess(GuessT playerGuess);
}
