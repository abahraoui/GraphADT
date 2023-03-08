import { useEffect, useState } from "react";
import { Edge, Node } from "vis-network";
import GraphService from "../GraphService";
import { IGraphEdge } from "../IGraphModel";
import GraphInner from "./GraphInner";

export default function Graph() {
  const [edges, setEdges] = useState<IGraphEdge[]>([]);

  useEffect(() => {
    if (edges.length === 0) load();
  }, []);

  const load = async () => {
    setEdges(await GraphService.getEdges());
  };

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

  const shuffled = Array(nodeList.length)
    .fill(null)
    .map((_v, i) => i)
    .sort(() => 0.5 - Math.random());

  const [startNodeIndex, endNodeIndex] = shuffled.slice(0, 2);
  if (nodeList.length != 0) {
    nodeList[startNodeIndex].color = {
      background: "lightgreen",
      highlight: "lightgreen",
      hover: "lightgreen",
      border: "green",
    };

    nodeList[endNodeIndex].color = {
      background: "#ff8484",
      highlight: "#ff8484",
      hover: "#ff8484",
      border: "red",
    };
  }

  if (edges.length === 0) return <>Loading...</>;

  return <GraphInner nodes={nodeList} edges={edgeList} />;
}
