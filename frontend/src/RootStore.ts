import { flow, Instance, types } from "mobx-state-tree";
import toast from "react-hot-toast";
import GraphService from "./GraphService";
import {
  DIFFICULTIES,
  GameStates,
  LOCAL_STORAGE_KEYS,
} from "./helpers/constants";
import { withSetPropAction } from "./helpers/withSetPropAction";
import { IGraphEdge } from "./IGraphModel";

const EdgeModel = types.model("Edge").props({
  from: types.string,
  to: types.string,
  weight: types.number,
});

const ScoreModel = types.model("Score").props({
  score: types.number,
  difficulty: types.enumeration(DIFFICULTIES),
  startNode: types.string,
  endNode: types.string,
  tries: types.number,
  seconds: types.number,
});

export const RootStoreModel = types
  .model("RootStore")
  .props({
    startNode: types.maybe(types.string),
    endNode: types.maybe(types.string),
    selectedInput: types.maybe(types.enumeration(["START", "END"])),
    chosenDifficulty: types.optional(types.enumeration(DIFFICULTIES), "Easy"),
    edges: types.maybe(types.array(EdgeModel)),
    isCreatingGraph: false,
    isLoadingGraph: false,
    scores: types.optional(types.array(ScoreModel), []),
    correctPath: types.maybe(types.array(types.string)),
    stateOfGame: types.optional(
      types.enumeration([
        GameStates.SETUP,
        GameStates.PLAYING,
        GameStates.REVIEWING,
      ]),
      GameStates.SETUP
    ),
  })
  .actions(withSetPropAction)
  .actions((self) => ({
    loadInitalGraph: flow(function* () {
      self.setProp("isLoadingGraph", true);
      const edges: IGraphEdge[] = yield GraphService.getEdges();
      self.setProp("edges", edges);
      self.setProp("isLoadingGraph", false);
    }),
    loadPreviousScores: () => {
      const scores: {
        score: number;
        difficulty: string;
        startNode: string;
        endNode: string;
        tries: number;
        seconds: number;
      }[] = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEYS.scores) ?? "[]");
      self.setProp("scores", scores);
    },
    saveScore: (score: number, tries: number, seconds: number) => {
      if (!self.chosenDifficulty || !self.startNode || !self.endNode)
        throw new Error("Cannot save score without difficulty and nodes.");
      const scores = [
        ...self.scores,
        {
          score,
          difficulty: self.chosenDifficulty,
          startNode: self.startNode,
          endNode: self.endNode,
          tries,
          seconds,
        },
      ];
      self.setProp("scores", scores);
      localStorage.setItem(LOCAL_STORAGE_KEYS.scores, JSON.stringify(scores));
    },
  }))
  .actions((self) => ({
    reset: flow(function* () {
      self.setProp("stateOfGame", GameStates.SETUP);
      self.setProp("startNode", undefined);
      self.setProp("endNode", undefined);
      self.setProp("chosenDifficulty", undefined);
      self.setProp("correctPath", undefined);
      yield self.loadInitalGraph();
    }),
    reviewAnswer: () => {
      self.setProp("stateOfGame", GameStates.REVIEWING);
    },
  }))
  .actions((self) => ({
    afterCreate: flow(function* () {
      yield self.loadInitalGraph();
      self.loadPreviousScores();
    }),
    startPlaying: flow(function* () {
      self.setProp("isCreatingGraph", true);
      if (!self.chosenDifficulty) {
        toast.error("Please choose a difficulty.");
        return;
      }
      const res: {
        edges: IGraphEdge[];
        startNodeKey: string;
        endNodeKey: string;
      } = yield GraphService.createGraph(
        self.chosenDifficulty,
        self.startNode,
        self.endNode
      );
      self.setProp("edges", res.edges);
      self.setProp("startNode", res.startNodeKey);
      self.setProp("endNode", res.endNodeKey);
      self.setProp("isCreatingGraph", false);
      self.setProp("stateOfGame", GameStates.PLAYING);
    }),
    submitGuess: flow(function* (guess: number) {
      yield toast.promise(
        (async () => {
          const answer = await GraphService.checkGuess(guess);
          if (answer.isCorrect) {
            self.saveScore(answer.score, answer.tries, answer.seconds);
            self.setProp("correctPath", answer.path);
            self.reviewAnswer();
            return `You guessed correctly in ${answer.tries} ${
              answer.tries === 1 ? "try" : "tries"
            }! Your score is ${answer.score}`;
          }
          throw new Error(
            `Incorrect guess. Try again! Hint: ${answer.feedback}`
          );
        })(),
        {
          loading: "Checking guess...",
          success: (answer: string) => answer,
          error: (error) => error?.message ?? "Something went wrong.",
        }
      );
    }),
    startNewGame: () => {
      self.reset();
    },
  }))
  .views((self) => ({
    get canStartGame() {
      return (
        !!self.chosenDifficulty &&
        (["Easy", "Medium", "Hard"].includes(self.chosenDifficulty) ||
          (self.chosenDifficulty === "Custom" &&
            !!self.startNode &&
            !!self.endNode))
      );
    },
    get correctPathEdges(): { from: string; to: string }[] {
      const res: { from: string; to: string }[] = [];
      if (!self.correctPath) return res;
      for (let i = 0; i < self.correctPath.length - 1; i++) {
        res.push({
          from: self.correctPath[i],
          to: self.correctPath[i + 1],
        });
      }
      return res;
    },
  }));

export interface RootStore extends Instance<typeof RootStoreModel> {}
