package Team19;

abstract class EdgeADT<NodeKey,EdgeWeight> {
    public NodeKey from;
    public NodeKey to;
    public EdgeWeight weight;

    public EdgeADT(NodeKey from, NodeKey to, EdgeWeight weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

}
