package Team19;

public interface IGameBase<NodeKey,FeedbackT,GuessT> {
    NodeKey createGraph(NodeKey start_node,  NodeKey end_node);

    void setStartNodeKey(NodeKey startingNode);

    void setEndNodeKey(NodeKey endingNode);

    NodeKey getStartNodeKey();

    NodeKey getEndNodeKey();

    FeedbackT checkGuess(GuessT playerGuess);
}