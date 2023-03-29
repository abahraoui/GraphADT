package Team19;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.jooby.annotations.GET;
import io.jooby.annotations.Path;
import io.jooby.annotations.QueryParam;

import java.util.ArrayList;

@Path("/")
public class MyController {

    private static GraphADT graph;

    public MyController(){
        graph = new Graph();
        graph.importGraph("src/main/resources/sampleInput.txt");
    }

    @GET("/")
    public String landingPage() {

        return "Hello Jooby!";
    }

    @GET("/createGraph")
    public String createGraph(@QueryParam String start_node, @QueryParam String end_node, @QueryParam String difficulty) {
        String startNode = start_node != null ? start_node : null;
        String endNode = end_node != null ? end_node : null;
        String diff = difficulty != null ? difficulty.toLowerCase() : "easy";
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
        Long startTime = System.nanoTime();
        //   graph.userPlayTime = Math.toIntExact(startTime*(10^9));
        response.addProperty("startNodeKey", graph.getStartNodeKey());
        response.addProperty("endNodeKey",graph.getEndNodeKey());
        response.add("edges", edgesJson);
        return new Gson().toJson(response);

    }

    @GET("/getEdges")
    public String getEdges() {
        return new Gson().toJson(graph.getEdges());

    }

    @GET("/checkGuess")
    public String checkGuess(@QueryParam String guess) {
        return graph.checkGuess(Integer.parseInt(guess));
    }


}

