package com.bridge18.expedition;


import akka.actor.ActorSystem;
import com.bridge18.expedition.api.LagomEquipmentService;
import com.lightbend.lagom.javadsl.testkit.ServiceTest;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.CharArrayBuffer;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.lightbend.lagom.javadsl.testkit.ServiceTest.defaultSetup;
import static org.junit.Assert.assertEquals;

public class CORSTest {
    static ActorSystem system;

    private static final ServiceTest.Setup setup = defaultSetup().withCassandra(true);

    private static ServiceTest.TestServer testServer;
    private static LagomEquipmentService testService;

    @BeforeClass
    public static void beforeAll(){
        system = ActorSystem.create("CORSTest");

        testServer = ServiceTest.startServer(setup);
        testService = testServer.client(LagomEquipmentService.class);
    }

    @Test
    public void test() throws URISyntaxException, IOException, ExecutionException, InterruptedException {
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        try {
            // Start the client
            httpclient.start();

            String serverUrl = "http://localhost:";
            String methodType = "GET";

            HttpOptions options = new HttpOptions(serverUrl + testServer.port() + "/v1/api/expedition/equipment");
            options.addHeader("Access-Control-Request-Method", methodType);
            options.addHeader("Access-Control-Request-Headers", "X-Requested-With");
            options.addHeader("Origin", "http://example.com");

            Future<HttpResponse> future = httpclient.execute(options, null);

            HttpResponse response = future.get();
            assertEquals(200, response.getStatusLine().getStatusCode());
            assertEquals("Origin", response.getHeaders("Vary")[0].getValue());
            assertEquals("3600", response.getHeaders("Access-Control-Max-Age")[0].getValue());
            assertEquals("http://example.com", response.getHeaders("Access-Control-Allow-Origin")[0].getValue());
            assertEquals("x-requested-with", response.getHeaders("Access-Control-Allow-Headers")[0].getValue());
            assertEquals(methodType, response.getHeaders("Access-Control-Allow-Methods")[0].getValue());
            assertEquals("true", response.getHeaders("Access-Control-Allow-Credentials")[0].getValue());
            assertEquals("0", response.getHeaders("Content-Length")[0].getValue());
        } finally {
            httpclient.close();
        }



    }
}
