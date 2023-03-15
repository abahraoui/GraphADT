package Team19;

import org.jooby.Jooby;
import com.google.gson.Gson;
import org.jooby.handlers.CorsHandler;

/**
 * @author jooby generator
 */
public class App extends Jooby {
    private static GraphADT graph;

    {
        use("*", new CorsHandler());
        get("/", () -> "Hello World!");
        get("/createGraph", req -> {
            graph = new Graph();
            graph.importGraph("src/main/resources/sampleInput.txt");
            if (!Boolean.parseBoolean(req.param("isRandom").value())) {
                String startNode = req.param("startNode").value();
                String endNode = req.param("endNode").value();
                graph.setStartNodeKey(startNode);
                graph.setEndNodeKey(endNode);
                System.out.println("Created Not Random graph");
            }else{
                //graph.setRandomStartNode();
               // graph.setRandomEndNode();
            }



        return 1;

        });
        get("/getEdges", () -> new Gson().toJson(graph.getEdges()));
        get("/checkGuess", req -> graph.checkGuess(Integer.parseInt(req.param("guess").value())));

    }

    public static void main(final String[] args) {
   //     graph = new Graph();
    //    graph.importGraph("src/main/resources/sampleInput.txt");
    //    graph.setStartNodeKey("0");
     //   graph.setEndNodeKey("16");
      //  System.out.println("correct len: "+ graph.correctLength);
        run(App::new, args);
    }
}
