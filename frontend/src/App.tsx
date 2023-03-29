import Graph from "./components/GraphController";
import GraphSettings from "./components/GraphSettings";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { useState } from "react";

const queryClient = new QueryClient();

export type ISelectedInput = "START" | "END" | undefined;

export default function App() {
  const [startNode, setStartNode] = useState<string | undefined>(undefined);
  const [endNode, setEndNode] = useState<string | undefined>(undefined);
  const [selectedInput, setSelectedInput] = useState<ISelectedInput>(undefined);

  return (
    <QueryClientProvider client={queryClient}>
      <div className="bg-neutral-200 h-screen">
        <div className="flex flex-col h-full">
          <GraphSettings
            startNode={startNode}
            endNode={endNode}
            selectedInput={selectedInput}
            setSelectedInput={setSelectedInput}
          />
          <div className="grow w-full">
            <Graph
              {...{
                startNode,
                endNode,
                setEndNode,
                setStartNode,
                selectedInput,
              }}
            />
          </div>
        </div>
      </div>
    </QueryClientProvider>
  );
}
