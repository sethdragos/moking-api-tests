package com.example.cucumber.mock;

import com.example.cucumber.util.FileReader;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.NottableString;

import java.io.IOException;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonSchemaBody.jsonSchemaFromResource;
import static org.mockserver.model.Not.not;

public class MockServer {

    public static final int SERVER_PORT = 8090;

    private static ClientAndServer mockServer;
    private static Header contentType = Header.header("Content-Type", "application/json");

    public static void start() throws IOException {
        mockServer = startClientAndServer(SERVER_PORT);

        mockServer
                .when(request("/rest/api/customer")
                        .withMethod("POST")
                        .withBody(jsonSchemaFromResource("schema/post_request_schema.json")))
                .respond(
                        response()
                                .withHeader(contentType)
                                .withStatusCode(201)
                                .withBody(FileReader.read("src/test/resources/output/post_response.json")));

        mockServer
                .when(request("/rest/api/customer")
                        .withMethod("POST")
                        .withBody(not(jsonSchemaFromResource("schema/post_request_schema.json"))))
                .respond(
                        response()
                                .withStatusCode(401));

        mockServer
                .when(request("/rest/api/customer/1")
                        .withMethod("GET"))
                .respond(
                        response()
                                .withHeader(contentType)
                                .withStatusCode(200)
                                .withBody(FileReader.read("src/test/resources/output/get_response.json")));

        mockServer
                .when(request()
                        .withPath(NottableString.not("/rest/api/customer/1"))
                        .withMethod("GET"))
                .respond(
                        response()
                                .withStatusCode(404));
    }

    public static void stop() {
        mockServer.stop();
    }
}
