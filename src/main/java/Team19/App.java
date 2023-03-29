package Team19;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jooby.Jooby;
import com.google.gson.Gson;
import org.jooby.handlers.CorsHandler;
import java.util.ArrayList;

/**
 * @author jooby generator
 */
public class App extends Jooby {
    private static GraphADT graph;

    {
        use("*", new CorsHandler());
        get("/createGraph", req -> {
            String startNode = req.param("startNode").isSet() ? req.param("startNode").value() : null;
            String endNode = req.param("endNode").isSet() ? req.param("endNode").value() : null;
            String diff = req.param("diff").isSet() ? req.param("diff").value().toLowerCase() : "easy";
            if (startNode == null) {
                graph.setRandomStartNode();
                graph.setRandomEndNode(diff);
            } else {
                graph.setStartNodeKey(startNode);
                graph.setEndNodeKey(endNode);
            }

            JsonArray edgesJson = new JsonArray();
            ArrayList<EdgeDTO> edges = graph.getEdges();
            edges.forEach(edge -> {
                JsonObject edgeJson = new JsonObject();
                edgeJson.addProperty("from", edge.from);
                edgeJson.addProperty("to", edge.to);
                edgeJson.addProperty("weight", edge.weight);
                edgesJson.add(edgeJson);
            });
            JsonObject response = new JsonObject();
            response.addProperty("startNodeKey", graph.getStartNodeKey());
            response.addProperty("endNodeKey",graph.getEndNodeKey());
            response.add("edges", edgesJson);
            return new Gson().toJson(response);
        });
        get("/checkGuess", req -> graph.checkGuess(Integer.parseInt(req.param("guess").value())));
        get("/getEdges", () -> new Gson().toJson(graph.getEdges())); //TODO delete

    }

    public static void main(final String[] args) {
        graph = new Graph();
        graph.importGraph("src/main/resources/sampleInput.txt");
        run(App::new, args);
    }
}
