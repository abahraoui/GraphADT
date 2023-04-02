package Team19;

public interface IGameDifficulty<NodeKey,DifficultyInputT> {
    NodeKey generateRandomStartNode();

    NodeKey generateRandomEndNode();

    void setDifficulty(DifficultyInputT diff);

    long calculateScore(long userPlayTime, Integer amountOfGuesses);

    NodeKey findShortestPathBasedOnDiff();

    NodeKey pickEndNodeBasedOnDiff(int PathLengthBasedOnDiff);
}
