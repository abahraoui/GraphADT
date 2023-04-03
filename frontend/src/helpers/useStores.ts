import { createContext, useContext } from "react"
import { RootStore, RootStoreModel } from "../RootStore"

const _rootStore = RootStoreModel.create({})

/**
 * The RootStoreContext provides a way to access
 * the RootStore in any screen or component.
 */
const RootStoreContext = createContext<RootStore>(_rootStore)

/**
 * A hook that screens and other components can use to gain access to
 * our stores:
 *
 * const rootStore = useStores()
 */
export const useStores = () => useContext(RootStoreContext)
