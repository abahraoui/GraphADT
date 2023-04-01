import Graph from "./components/GraphController";
import GraphSettings from "./components/GraphSettings";

export default function App() {
  return (
    <div className="h-screen bg-neutral-200">
      <div className="flex h-full flex-col">
        <GraphSettings />
        <div className="w-full grow">
          <Graph />
        </div>
      </div>
    </div>
  );
}
