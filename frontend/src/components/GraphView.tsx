import { observer } from "mobx-react-lite";
import { useEffect, useState } from "react";
import { toast } from "react-hot-toast";
import { Edge, Node } from "vis-network";
import { useStores } from "../helpers/useStores";
import useVisNetwork from "../useVisNetwork";
import { LoadingSpinner } from "./LoadingSpinner";

interface IProps {
  edges: Edge[];
  nodes: Node[];
  height: number;
}

export default observer(function GraphInner(props: IProps) {
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const { selectedInput, setProp, startNode, endNode, isPlaying } = useStores();
  const { ref, network } = useVisNetwork({
    options: { autoResize: true, height: `${props.height}px` },
    edges: props.edges,
    nodes: props.nodes,
  });

  const onNodeClick = (ctx: { nodes: number[] }) => {
    if (isNaN(ctx.nodes[0]) || isPlaying) return;
    const newNodeKey = `${ctx.nodes[0]}`;

    if (selectedInput === "START") {
      if (endNode === newNodeKey) {
        toast.error("Start and end node cannot be the same!");
        return;
      }
      setProp("startNode", newNodeKey);
    } else if (selectedInput === "END") {
      if (startNode === newNodeKey) {
        toast.error("Start and end node cannot be the same!");
        return;
      }
      setProp("endNode", newNodeKey);
    }
  };

  useEffect(() => {
    if (!network) return;
    network.once("afterDrawing", () => setIsLoading(false));
    network.on("click", onNodeClick);
    return () => {
      network.off("click", onNodeClick);
    };
  }, [network, selectedInput]);

  return (
    <div className="h-full">
      {isLoading && (
        <div className="flex justify-center">
          <LoadingSpinner />
        </div>
      )}
      <div className={isLoading ? "" : "h-full"} ref={ref} />
    </div>
  );
});
