package Team19;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//import org.jooby.Jooby;
import com.google.gson.Gson;
import io.jooby.Route;
import io.jooby.annotations.GET;
//import org.jooby.handlers.CorsHandler;
import java.util.ArrayList;
import io.jooby.Jooby;


public class App extends Jooby {


   private static GraphADT graph;
    {
        mvc(new MyController());
    }


    public static void main(final String[] args) {

        runApp(args, App::new);

    }
}
