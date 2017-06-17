package com.cassiomolin.example.api;


import com.cassiomolin.example.Application;
import com.cassiomolin.example.domain.Message;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.websocket.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class ApplicationTest {

    @BeforeClass
    public static void beforeClass() {
        Application.startServer(9090);
    }

    @AfterClass
    public static void afterClass() {
        Application.stopServer();
    }

    @Test
    public void test() throws URISyntaxException, IOException, DeploymentException, InterruptedException {

        CountDownLatch messageLatch = new CountDownLatch(2);

        ClientEndpointConfig config = ClientEndpointConfig.Builder.create().build();

        WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
        webSocketContainer.connectToServer(new Endpoint() {

            @Override
            public void onOpen(Session session, EndpointConfig config) {
                session.addMessageHandler(String.class, message -> {
                    System.out.println("Received message: " + message);
                    messageLatch.countDown();
                });
            }

            @Override
            public void onError(Session session, Throwable thr) {
                messageLatch.countDown();
            }

        }, config, new URI("ws://localhost:9090/push"));

        messageLatch.await(1, TimeUnit.SECONDS);

        Message message = new Message();
        message.setMessage("Hey");

        Client restClient = ClientBuilder.newClient();
        restClient.register(JacksonJaxbJsonProvider.class);

        Response response = restClient.target("http://localhost:9090/api/messages")
                .request().post(Entity.entity(message, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        messageLatch.await(1, TimeUnit.SECONDS);
    }
}

