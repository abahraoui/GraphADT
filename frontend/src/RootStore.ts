import { Instance, types } from "mobx-state-tree";
import { DIFFICULTIES } from "./helpers/constants";
import { withSetPropAction } from "./helpers/withSetPropAction";

export const RootStoreModel = types
  .model("RootStore")
  .props({
    startNode: types.maybe(types.string),
    endNode: types.maybe(types.string),
    selectedInput: types.maybe(types.enumeration(["START", "END"])),
    chosenDifficulty: types.maybe(types.enumeration(DIFFICULTIES)),
  })
  .actions(withSetPropAction)
  .actions((self) => ({
    // loadExercises: () => self.setProp("exercises", api.getExercises()),
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
