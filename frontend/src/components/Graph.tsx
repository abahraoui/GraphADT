import { Edge, Node } from "vis-network";
import { edges } from "../edges";
import useVisNetwork from "../useVisNetwork";

export default function Graph() {
  const edgeList: Edge[] = edges.map((e, i) => ({
    id: i,
    ...e,
    label: `${e.weight}`,
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

  const { ref, network } = useVisNetwork({
    options: {},
    edges: edgeList,
    nodes: nodeList,
  });

  const handleClick = () => {
    if (!network) return;

    network.focus(5);
  };

  // useEffect(() => {
  //   if (!network) return;

  //   network.once("beforeDrawing", () => {
  //     network.focus(5);
  //   });
  //   network.setSelection({
  //     edges: [1, 2, 3, 4, 5],
  //     nodes: [1, 2, 3, 4, 5]
  //   });
  // }, [network]);

  return <div className="w-full flex-1" ref={ref} />;
}
