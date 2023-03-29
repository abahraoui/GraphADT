import { useQuery } from "@tanstack/react-query";
import { Edge, Node } from "vis-network";
import GraphService from "../GraphService";
import { IGraphEdge } from "../IGraphModel";
import GraphInner from "./GraphView";

interface IProps {
  startNode: string | undefined;
  endNode: string | undefined;
  setStartNode: (node: string | undefined) => void;
  setEndNode: (node: string | undefined) => void;
  selectedInput: "START" | "END" | undefined;
}

export default function Graph(props: IProps) {
  const {
    isLoading,
    isError,
    error,
    data: edges,
  } = useQuery<IGraphEdge[]>({
    queryKey: ["getEdges"],
    queryFn: GraphService.getEdges,
  });

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

  const startNodeIndex = nodeList.findIndex((n) => n.id == props.startNode);
  const endNodeIndex = nodeList.findIndex((n) => n.id == props.endNode);
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
      key={`${props.startNode}-${props.endNode}`}
      nodes={nodeList}
      edges={edgeList}
      height={(innerHeight * 3) / 4}
      setStartNode={props.setStartNode}
      setEndNode={props.setEndNode}
      selectedInput={props.selectedInput}
    />
  );
}
