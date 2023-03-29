import Graph from "./components/GraphController";
import GraphSettings from "./components/GraphSettings";

export default function App() {
  return (
    <div className="bg-neutral-200 h-screen">
      <div className="flex flex-col h-full">
        <GraphSettings/>
        <div className="grow w-full">
          <Graph />
        </div>
      </div>
    </div>
  );
}
