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
        run(App::new, args);
    }


}
