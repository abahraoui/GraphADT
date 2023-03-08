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
        get("/getEdges", () -> new Gson().toJson(graph.getEdges()));
    }

    public static void main(final String[] args) {
        graph = new Graph();
        graph.importGraph("src/main/resources/sampleInput.txt");
        graph.setStartNodeKey("0");
        graph.setEndNodeKey("16");
        System.out.println("correct len: "+ graph.correctLength);
        run(App::new, args);
    }
}
