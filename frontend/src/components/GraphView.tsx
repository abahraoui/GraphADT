import { useEffect, useState } from "react";
import { Edge, Node } from "vis-network";
import useVisNetwork from "../useVisNetwork";

interface IProps {
  edges: Edge[];
  nodes: Node[];
  height: number;
  setStartNode: (node: string | undefined) => void;
  setEndNode: (node: string | undefined) => void;
  selectedInput: "START" | "END" | undefined;
}

export default function GraphInner(props: IProps) {
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const { ref, network } = useVisNetwork({
    options: { autoResize: true, height: `${props.height}px` },
    edges: props.edges,
    nodes: props.nodes,
  });

  const onNodeClick = (ctx: { nodes: number[] }) => {
    if (isNaN(ctx.nodes[0])) return;
    const newNodeKey = `${ctx.nodes[0]}`;

    if (props.selectedInput === "START") props.setStartNode(newNodeKey);
    else if (props.selectedInput === "END") props.setEndNode(newNodeKey);
  };

  useEffect(() => {
    if (!network) return;
    network.once("afterDrawing", () => setIsLoading(false));
    network.on("click", onNodeClick);
    return () => {
      network.off("click", onNodeClick);
    };
  }, [network, props.selectedInput]);

  return (
    <div className="h-full">
      {isLoading && <span>Loading...</span>}
      <div className={isLoading ? "" : "h-full"} ref={ref} />
    </div>
  );
}
