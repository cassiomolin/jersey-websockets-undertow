package com.cassiomolin.example.api;


import com.cassiomolin.example.Application;
import com.cassiomolin.example.domain.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApplicationTest {

    private static final String WELCOME_MESSAGE = "Welcome";
    private static final String BROADCAST_MESSAGE = "Hey";

    private static final int TEST_PORT = 9090;
    private static final String REST_API_BASE_URI = "http://localhost:9090/api";
    private static final String WEBSOCKETS_BASE_URI = "ws://localhost:9090";

    @BeforeClass
    public static void beforeClass() {
        Application.startServer(TEST_PORT);
    }

    @AfterClass
    public static void afterClass() {
        Application.stopServer();
    }

    @Test
    public void test() throws URISyntaxException, IOException, DeploymentException, InterruptedException {

        CountDownLatch countDown = new CountDownLatch(2);

        ClientEndpointConfig config = ClientEndpointConfig.Builder.create()
                .decoders(Arrays.asList(MessageDecoder.class)).build();

        WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
        webSocketContainer.connectToServer(new Endpoint() {

            @Override
            public void onOpen(Session session, EndpointConfig config) {
                session.addMessageHandler(Message.class, message -> {
                    System.out.println("Received message: " + message.getMessage());

                    switch ((int) countDown.getCount()) {

                        case 2:
                            assertTrue(message.getMessage().startsWith(WELCOME_MESSAGE));
                            break;

                        case 1:
                            assertEquals(BROADCAST_MESSAGE, message.getMessage());
                            break;
                    }

                    countDown.countDown();
                });
            }

            @Override
            public void onError(Session session, Throwable thr) {
                countDown.countDown();
            }

        }, config, new URI(WEBSOCKETS_BASE_URI + "/push"));

        countDown.await(5, TimeUnit.SECONDS);

        Message message = new Message();
        message.setMessage(BROADCAST_MESSAGE);

        Client restClient = ClientBuilder.newClient();
        restClient.register(JacksonJaxbJsonProvider.class);

        Response response = restClient.target(REST_API_BASE_URI).path("messages")
                .request().post(Entity.entity(message, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        countDown.await(5, TimeUnit.SECONDS);
    }

    private static class MessageDecoder implements Decoder.Text<Message> {

        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public void init(EndpointConfig endpointConfig) {

        }

        @Override
        public void destroy() {

        }

        @Override
        public Message decode(String s) throws DecodeException {
            try {
                return mapper.readValue(s, Message.class);
            } catch (IOException e) {
                throw new DecodeException(s, "Cannot decode message", e);
            }
        }

        @Override
        public boolean willDecode(String s) {
            return true;
        }
    }
}

