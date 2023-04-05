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
        game.graph.importGraph("/sampleInput.txt");
    }

    @GET("/")
    public String landingPage() {
        return "Welcome to the Graph Game API!\n\n" + "The endpoints are:\n" +
                "  GET /\n" +
                "  GET /createGraph  with parameters start_node and end_node or difficulty.\n" +
                "  GET /getEdges\n" +
                "  GET /checkGuess with a single parameter guess.\n\n"
                + "To add parameters to endpoints add to localhost: '/endpoint_name?param_name=value'\n" +
                "If there is more than one parameter do: '/endpoint_name?param_name1=value1&param_name2=value2'";
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

