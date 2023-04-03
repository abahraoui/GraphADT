package Team19;


import io.jooby.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.Objects;

public class AppTest {

    static OkHttpClient client = new OkHttpClient();

    /**
     * This is a simple helper that checks if a String is an integer.
     *
     * @param input this parameter is the String that will be checked.
     */
    private boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * This is the integrationTest that will test that the API works correctly.
     *
     * @param serverPort this parameter is the server port of localhost on which the app is run for testing.
     */
    @JoobyTest(App.class)
    public void integrationTest(int serverPort) throws IOException, InterruptedException {

        /*
          This is the landing endpoint of our API, that contains a short guideline on how to use it.
          We check if it's working by asserting the Status code of the request and by asserting that
          the whole body is displayed.
         */
        Request req = new Request.Builder()
                .url("http://localhost:" + serverPort + "/")
                .build();
        Response rsp = client.newCall(req).execute();
        Assertions.assertEquals(StatusCode.OK.value(), rsp.code());
        Assertions.assertEquals("Welcome to the Graph Game API!\n\n" + "The endpoints are:\n" +
                "  GET /\n" +
                "  GET /createGraph  with parameters start_node and end_node or difficulty.\n" +
                "  GET /getEdges\n" +
                "  GET /checkGuess with a single parameter guess.\n\n"
                + "To add parameters to endpoints add to localhost: '/endpoint_name?param_name=value'\n" +
                "If there is more than one parameter do: '/endpoint_name?param_name1=value1&param_name2=value2'",
                Objects.requireNonNull(rsp.body()).string());
        rsp.close();

        /*
          This is the creatGraph endpoint of our API, we check that each difficulty is set correctly
          by asserting that the start and end nodes are randomly assigned, by checking if they are integers.
         */
        req = new Request.Builder()
                .url("http://localhost:" + serverPort + "/createGraph?difficulty=easy")
                .build();
        rsp = client.newCall(req).execute();
        Assertions.assertEquals(StatusCode.OK.value(), rsp.code());

        JSONObject obj = new JSONObject(Objects.requireNonNull(rsp.body()).string());
        Assertions.assertTrue(isInt(obj.getString("endNodeKey")));
        Assertions.assertTrue(isInt(obj.getString("startNodeKey")));

        req = new Request.Builder()
                .url("http://localhost:" + serverPort + "/createGraph?difficulty=medium")
                .build();
        rsp = client.newCall(req).execute();
        Assertions.assertEquals(StatusCode.OK.value(), rsp.code());

        obj = new JSONObject(Objects.requireNonNull(rsp.body()).string());
        Assertions.assertTrue(isInt(obj.getString("endNodeKey")));
        Assertions.assertTrue(isInt(obj.getString("startNodeKey")));

        req = new Request.Builder()
                .url("http://localhost:" + serverPort + "/createGraph?difficulty=hard")
                .build();
        rsp = client.newCall(req).execute();
        Assertions.assertEquals(StatusCode.OK.value(), rsp.code());

        obj = new JSONObject(Objects.requireNonNull(rsp.body()).string());
        Assertions.assertTrue(isInt(obj.getString("endNodeKey")));
        Assertions.assertTrue(isInt(obj.getString("startNodeKey")));

        /*
         Here we assert the custom difficulty by adding start_node and end_node parameters,
         and we check they are correctly assigned.
         */
        req = new Request.Builder()
                .url("http://localhost:" + serverPort + "/createGraph?start_node=1&end_node=8")
                .build();
        rsp = client.newCall(req).execute();
        Assertions.assertEquals(StatusCode.OK.value(), rsp.code());
        obj = new JSONObject(Objects.requireNonNull(rsp.body()).string());
        Assertions.assertEquals("8", obj.getString("endNodeKey"));
        Assertions.assertEquals("1", obj.getString("startNodeKey"));
        rsp.close();

        /*
         This is the getEdges endpoint which can be used to display the graph fully before starting the game,
         we assert the size of the response payload as it never changes.
         */
        req = new Request.Builder()
                .url("http://localhost:" + serverPort + "/getEdges")
                .build();
        rsp = client.newCall(req).execute();
        Assertions.assertEquals(StatusCode.OK.value(), rsp.code());
        Assertions.assertEquals(4513, Objects.requireNonNull(rsp.body()).string().length());
        rsp.close();

        /*
          This is the checkGuess endpoint that will assert that the player guess is right or wrong,
          we use start node '1' and end node '8' for this example game.
          Here our guess '2' is lower than the actual correct answer, we assert the response is a hint called 'HIGHER'.
         */
        req = new Request.Builder()
                .url("http://localhost:" + serverPort + "/checkGuess?guess=2")
                .build();
        rsp = client.newCall(req).execute();
        Assertions.assertEquals(StatusCode.OK.value(), rsp.code());
        Assertions.assertEquals("HIGHER", Objects.requireNonNull(rsp.body()).string());
        rsp.close();

        /*
          Here our guess '10' is higher than the actual correct answer, we assert the response is a hint called 'LOWER'.
         */
        req = new Request.Builder()
                .url("http://localhost:" + serverPort + "/checkGuess?guess=10")
                .build();
        rsp = client.newCall(req).execute();
        Assertions.assertEquals(StatusCode.OK.value(), rsp.code());
        Assertions.assertEquals("LOWER", Objects.requireNonNull(rsp.body()).string());
        rsp.close();

        Thread.sleep(2000);
        /*
          Here our guess '7' is the correct answer, we assert the response is 'CORRECT your score was 1125',
          we put our thread to sleep for 2 seconds as our score depends on the number of guesses and the time played.
         */
        req = new Request.Builder()
                .url("http://localhost:" + serverPort + "/checkGuess?guess=7")
                .build();
        rsp = client.newCall(req).execute();
        Assertions.assertEquals(StatusCode.OK.value(), rsp.code());
        Assertions.assertEquals("CORRECT your score was 1125", Objects.requireNonNull(rsp.body()).string());
        rsp.close();
    }

}
