import { useEffect } from "react";
import { Edge, Node } from "vis-network";
import useVisNetwork from "../useVisNetwork";

interface IProps {
  edges: Edge[];
  nodes: Node[];
}

export default function GraphInner(props: IProps) {
  const { ref, network } = useVisNetwork({
    options: {},
    edges: props.edges,
    nodes: props.nodes,
  });

  useEffect(() => {
    if (!network) return;
    
  }, [network]);

  return <div className="w-full flex-1" ref={ref} />;
}
