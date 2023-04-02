package Team19;

public interface IGameDifficulty<NodeKey,DifficultyInputT> {
    NodeKey generateRandomStartNode();

    NodeKey generateRandomEndNode();

    void setDifficulty(DifficultyInputT diff);

    ScoreT calculateScore();
}
