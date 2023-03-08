import { useEffect, useState } from "react";
import { Edge, Node } from "vis-network";
import useVisNetwork from "../useVisNetwork";

interface IProps {
  edges: Edge[];
  nodes: Node[];
  height: number;
}

export default function GraphInner(props: IProps) {
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const { ref, network } = useVisNetwork({
    options: { autoResize: true, height: `${props.height}px` },
    edges: props.edges,
    nodes: props.nodes,
  });

  useEffect(() => {
    if (!network) return;
    network.once("afterDrawing", () => setIsLoading(false));
  }, [network]);

  return (
    <div className="h-full">
      {isLoading && <span>Loading...</span>}
      <div className={isLoading ? "" : "h-full"} ref={ref} />
    </div>
  );
}
