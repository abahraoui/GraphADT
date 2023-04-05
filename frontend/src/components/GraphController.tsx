import { observer } from "mobx-react-lite";
import { Edge, Node } from "vis-network";
import { useStores } from "../helpers/useStores";
import GraphInner from "./GraphView";
import { LoadingSpinner } from "./LoadingSpinner";

export default observer(function Graph() {
  const {
    startNode,
    endNode,
    edges,
    isLoadingGraph,
    stateOfGame,
    correctPathEdges,
  } = useStores();

  if (!edges || isLoadingGraph)
    return (
      <div className="flex h-full items-center justify-center">
        <LoadingSpinner />
      </div>
    );
  // if (isError) return <>Error {(error as any)?.message ?? ""}!</>;

  const edgeList: Edge[] = edges.map((e, i) => {
    const shouldHighlight = correctPathEdges.some(
      (c) =>
        (c.from == e.from && c.to == e.to) || (c.from == e.to && c.to == e.from)
    );
    return {
      id: i,
      ...e,
      label: `${e.weight}`,
      length: e.weight * 20,
      width: shouldHighlight ? 5 : 1,
      color: shouldHighlight ? "red" : "#3481ea",
    };
  });

  const nodeList: Node[] = [
    ...new Set([...edges.map((e) => e.from), ...edges.map((e) => e.to)]),
  ].map((n) => ({
    id: n,
    label: `${n}`,
    font: {
      size: edgeList.filter((e) => e.from == n || e.to == n).length + 16,
    },
  }));

  const startNodeIndex = nodeList.findIndex((n) => n.id == startNode);
  const endNodeIndex = nodeList.findIndex((n) => n.id == endNode);
  if (nodeList.length != 0) {
    if (startNodeIndex !== -1)
      nodeList[startNodeIndex].color = {
        background: "lightgreen",
        highlight: "lightgreen",
        hover: "lightgreen",
        border: "green",
      };

    if (endNodeIndex !== -1)
      nodeList[endNodeIndex].color = {
        background: "#ff8484",
        highlight: "#ff8484",
        hover: "#ff8484",
        border: "red",
      };
  }

  return (
    <GraphInner
      key={`${startNode}-${endNode}-${edges.length}-${stateOfGame}`}
      nodes={nodeList}
      edges={edgeList}
      height={(innerHeight * 3) / 4}
    />
  );
});
