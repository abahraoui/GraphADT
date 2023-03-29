package Team19;

import io.jooby.CorsHandler;
import io.jooby.Jooby;


public class App extends Jooby {


    {
        decorator(new CorsHandler());
        mvc(new MyController());
    }


    public static void main(final String[] args) {

        runApp(args, App::new);

    }
}
