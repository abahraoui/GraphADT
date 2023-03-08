import Graph from "./components/GraphController";

export default function App() {
  return (
    <div className="bg-neutral-200 h-screen">
      <div className="flex flex-col h-full">
        <div className="shadow bg-neutral-100 m-4 rounded-lg">
          <div className="flex h-full items-center gap-4 p-4 justify-between">
            <h1 className="text-4xl">The Graph Game</h1>
            <button className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded shadow text-lg">
              Play
            </button>
          </div>
        </div>
        <div className="grow w-full">
          <Graph />
        </div>
      </div>
    </div>
  );
}
