import Graph from "./components/GraphController";
import GraphSettings from "./components/GraphSettings";
import { Toaster } from "react-hot-toast";

export default function App() {
  return (
    <>
      <div>
        <Toaster position="bottom-center" />
      </div>
      <div className="h-screen bg-neutral-200">
        <div className="flex h-full flex-col">
          <GraphSettings />
          <div className="w-full grow">
            <Graph />
          </div>
        </div>
      </div>
    </>
  );
}
