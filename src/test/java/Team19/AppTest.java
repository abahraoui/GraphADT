package Team19;


import static org.junit.Assert.assertEquals;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;

import org.jooby.test.JoobyRule;

import org.junit.ClassRule;
import org.junit.Test;


/**
 * @author jooby generator
 */
public class AppTest {

    /**
     * One app/server for all the test of this class. If you want to start/stop a new server per test,
     * remove the static modifier and replace the {@link ClassRule} annotation with {@link}.
     */
    @ClassRule
    public static JoobyRule app = new JoobyRule(new App());


    @Test
    public void integrationTest() throws Throwable {

        HttpUriRequest request = new HttpGet("http://localhost:8080/");
        CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals("HTTP/1.1 200 OK", httpResponse.getStatusLine().toString());

        HttpUriRequest request2 = new HttpGet("http://localhost:8080/createGraph");
        httpResponse = HttpClientBuilder.create().build().execute(request2);
        assertEquals("HTTP/1.1 200 OK", httpResponse.getStatusLine().toString());

        HttpUriRequest request3 = new HttpGet("http://localhost:8080/checkGuess?guess=2");
        httpResponse = HttpClientBuilder.create().build().execute(request3);
        assertEquals("HTTP/1.1 200 OK", httpResponse.getStatusLine().toString());
    }


}
