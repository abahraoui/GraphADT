import { IGraphEdge } from "./IGraphModel";

const API_URL = "http://localhost:8080";

export default {
  getEdges: async (): Promise<IGraphEdge[]> => {
    return fetch(`${API_URL}/getEdges`)
      .then((res) => {
        if (res.ok) return res.json();
        throw Error("Unexpected error happened.");
      })
      .then((edges) =>
        edges
          .map(
            (edge: {
              from: string | number;
              to: string | number;
              weight: string | number;
            }) => ({
              from: +edge.from,
              to: +edge.to,
              weight: +edge.weight,
            })
          )
      );
  },
};
