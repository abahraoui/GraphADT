package Team19;

public interface IGameDifficulty<NodeKey,DifficultyInputT,ScoreT> {
    NodeKey generateStartNodeBasedOnDifficulty();

    NodeKey generateEndNodeBasedOnDifficulty();

    void setDifficulty(DifficultyInputT diff);

    ScoreT calculateScore();
}
