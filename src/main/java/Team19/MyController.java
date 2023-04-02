package Team19;

import com.google.gson.Gson;

import io.jooby.annotations.GET;
import io.jooby.annotations.Path;
import io.jooby.annotations.QueryParam;


@Path("/")
public class MyController {

    private static Game game;

    public MyController(){
        game = new Game();
        game.graph.importGraph("src/main/resources/sampleInput.txt");
    }

    @GET("/")
    public String landingPage() {
        return "Hello Jooby!";
    }

    @GET("/createGraph")
    public String createGraph(@QueryParam String start_node, @QueryParam String end_node, @QueryParam String difficulty) {
        return game.createGraph(start_node, end_node, difficulty);
    }

    @GET("/getEdges")
    public String getEdges() {
        return new Gson().toJson(game.graph.getEdges());
    }

    @GET("/checkGuess")
    public String checkGuess(@QueryParam String guess) {
        return game.checkGuess(Double.parseDouble(guess));
    }
}

