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
    run(App::new, args);
  }
      GraphADT graph = new GraphADT() {
        @Override
        public int pathLengthBetweenStartAndEndNode() {
          return 0;
        }

        @Override
        public boolean importGraph(String input) {
          return false;
        }
      };
}
