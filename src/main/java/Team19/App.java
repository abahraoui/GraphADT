package Team19;

import org.jooby.Jooby;

/**
 * @author jooby generator
 */
public class App extends Jooby {

    {
        get("/", () -> "Hello World!");
    }

    public static void main(final String[] args) {
        GraphADT graph = new Graph();
        graph.importGraph("src/main/resources/sampleInput.txt");
        graph.setStartNodeKey("0");
        graph.setEndNodeKey("16");
        System.out.println("correct len: "+ graph.correctLength);
        run(App::new, args);
    }
}
