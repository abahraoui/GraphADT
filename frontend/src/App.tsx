import Graph from "./components/GraphController";
import GraphSettings from "./components/GraphSettings";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

const queryClient = new QueryClient();

export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <div className="h-screen bg-neutral-200">
        <div className="flex h-full flex-col">
          <GraphSettings />
          <div className="w-full grow">
            <Graph />
          </div>
        </div>
      </div>
    </QueryClientProvider>
  );
}
