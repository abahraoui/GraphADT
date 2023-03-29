import Graph from "./components/GraphController";
import GraphSettings from "./components/GraphSettings";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

const queryClient = new QueryClient();

export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <div className="bg-neutral-200 h-screen">
        <div className="flex flex-col h-full">
          <GraphSettings/>
          <div className="grow w-full">
            <Graph />
          </div>
        </div>
      </div>
    </QueryClientProvider>
  );
}
