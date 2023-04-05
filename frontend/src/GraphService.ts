import { toast } from "react-hot-toast";
import { IGraphEdge } from "./IGraphModel";

const API_URL = "http://localhost:8080";

type IGuessResponse =
  | {
      isCorrect: false;
      feedback: string;
    }
  | {
      isCorrect: true;
      feedback: string;
      path: string[];
      score: number;
      tries: number;
      seconds: number;
    };

export default {
  getEdges: async (): Promise<IGraphEdge[]> => {
    return fetch(`${API_URL}/getEdges`).then((res) => {
      if (res.ok) return res.json();
      throw Error("Unexpected error happened.");
    });
  },

  checkGuess: async (guess: number): Promise<IGuessResponse> => {
    return fetch(`${API_URL}/checkGuess?guess=${guess}`)
      .then((res) => {
        if (res.ok) return res.json();
        throw Error("Unexpected error happened.");
      })
      .catch((err) => {
        console.error(err);
        toast.error("Unexpected error happened.");
        throw Error("Unexpected error happened.");
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
