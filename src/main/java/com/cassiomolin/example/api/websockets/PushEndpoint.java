package com.cassiomolin.example.api.websockets;

import com.cassiomolin.example.domain.Message;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * WebSocket endpoint to push {@link Message}s to the clients.
 *
 * @author cassiomolin
 */
@Dependent
@ServerEndpoint(
        value = "/push",
        encoders = MessageEncoder.class,
        configurator = CdiAwareConfigurator.class)
public class PushEndpoint {

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    private static final Logger logger = Logger.getLogger(PushEndpoint.class.getName());

    @OnOpen
    public void onOpen(Session session) {

        sessions.add(session);
        logger.info(String.format("Session %s opened", session.getId()));

        Message message = new Message();
        message.setMessage("Welcome");
        session.getAsyncRemote().sendObject(message);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        logger.info(String.format("Session %s closed", session.getId()));
    }

    /**
     * Broadcasts the message to all sessions.
     *
     * @param message
     */
    public void broadcast(@Observes Message message) {

        logger.info(String.format("Broadcasting \"%s\" to %d sessions", message.getMessage(), sessions.size()));
        synchronized (sessions) {
            sessions.forEach(session -> {
                if (session.isOpen()) {
                    session.getAsyncRemote().sendObject(message);
                }
            });
        }
    }
}
