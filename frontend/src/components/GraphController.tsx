import { useQuery } from "@tanstack/react-query";
import { observer } from "mobx-react-lite";
import { Edge, Node } from "vis-network";
import GraphService from "../GraphService";
import { useStores } from "../helpers/useStores";
import { IGraphEdge } from "../IGraphModel";
import GraphInner from "./GraphView";

export default observer(function Graph() {
  const {
    isLoading,
    isError,
    error,
    data: edges,
  } = useQuery<IGraphEdge[]>({
    queryKey: ["getEdges"],
    queryFn: GraphService.getEdges,
  });

  const { startNode, endNode } = useStores();

  if (isLoading) return <>Loading...</>;
  if (isError) return <>Error {(error as any)?.message ?? ""}!</>;

  const edgeList: Edge[] = edges.map((e, i) => ({
    id: i,
    ...e,
    label: `${e.weight}`,
    length: e.weight * 20,
    color: "#3481ea",
  }));

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
      key={`${startNode}-${endNode}`}
      nodes={nodeList}
      edges={edgeList}
      height={(innerHeight * 3) / 4}
    />
  );
});
