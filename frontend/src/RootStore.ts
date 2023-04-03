import { flow, Instance, types } from "mobx-state-tree";
import GraphService from "./GraphService";
import { DIFFICULTIES } from "./helpers/constants";
import { withSetPropAction } from "./helpers/withSetPropAction";
import { IGraphEdge } from "./IGraphModel";

const EdgeModel = types.model("Edge").props({
  from: types.string,
  to: types.string,
  weight: types.number,
});

export const RootStoreModel = types
  .model("RootStore")
  .props({
    startNode: types.maybe(types.string),
    endNode: types.maybe(types.string),
    selectedInput: types.maybe(types.enumeration(["START", "END"])),
    chosenDifficulty: types.maybe(types.enumeration(DIFFICULTIES)),
    edges: types.maybe(types.array(EdgeModel)),
    isPlaying: false,
    isCreatingGraph: false,
    isLoadingGraph: false,
  })
  .actions(withSetPropAction)
  .actions((self) => ({
    loadInitalGraph: flow(function* () {
      self.setProp("isLoadingGraph", true);
      const edges: IGraphEdge[] = yield GraphService.getEdges();
      self.setProp("edges", edges);
      self.setProp("isLoadingGraph", false);
    }),
  }))
  .actions((self) => ({
    afterCreate: flow(function* () {
      yield self.loadInitalGraph();
    }),
    startPlaying: flow(function* () {
      self.setProp("isCreatingGraph", true);
      if (!self.chosenDifficulty) {
        alert("Please choose a difficulty.");
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
      self.setProp("isPlaying", true);
    }),
    stopPlaying: flow(function* () {
      self.setProp("isPlaying", false);
      self.setProp("startNode", undefined);
      self.setProp("endNode", undefined);
      self.setProp("chosenDifficulty", undefined);
      yield self.loadInitalGraph();
    }),
    submitGuess: flow(function* (guess: number) {
      const answer: string = yield GraphService.checkGuess(guess);
      alert(answer);
    }),
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
  }));

export interface RootStore extends Instance<typeof RootStoreModel> {}
