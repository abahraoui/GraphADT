import { IGraphEdge } from "./IGraphModel";

const API_URL = "http://localhost:8080";

export default {
  getEdges: async (): Promise<IGraphEdge[]> => {
    return fetch(`${API_URL}/getEdges`).then((res) => {
      if (res.ok) return res.json();
      throw Error("Unexpected error happened.");
    });
  },

  checkGuess: async (guess: number) => {
    return fetch(`${API_URL}/checkGuess?guess=${guess}`)
      .then((res) => {
        if (res.ok) return res.json();
        throw Error("Unexpected error happened.");
      })
      .then((answer) => {
        return answer.toString();
      });
  },

  createGraph: async (
    diff: String,
    startNode?: String,
    endNode?: String
  ): Promise<{
    edges: IGraphEdge[];
    startNodeKey: string;
    endNodeKey: string;
  }> => {
    let url = `${API_URL}/createGraph?`;
    if (startNode) url += `start_node=${startNode}&`;
    if (endNode) url += `end_node=${endNode}&`;
    url += `difficulty=${diff}`;
    return fetch(url).then((res) => {
      if (res.ok) return res.json();
      throw Error("Unexpected error happened.");
    });
  },
};
