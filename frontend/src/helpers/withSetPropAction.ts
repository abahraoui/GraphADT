import { IStateTreeNode, SnapshotIn } from "mobx-state-tree"

export const withSetPropAction = <T extends IStateTreeNode>(mstInstance: T) => ({
  setProp<K extends keyof SnapshotIn<T>, V extends SnapshotIn<T>[K]>(field: K, newValue: V) {
    // @ts-ignore - for some reason TS complains about this, but it still works fine
    mstInstance[field] = newValue
  },
})
