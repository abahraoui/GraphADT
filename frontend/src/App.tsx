import Graph from "./components/Graph";

export default function App() {
  return (
    <div className="bg-neutral-200 h-screen">
      <div className="flex flex-col h-full">
        {/* <h1 className="text-center">Hammy is a gayboi</h1> */}
        <Graph />
      </div>
    </div>
  );
}
