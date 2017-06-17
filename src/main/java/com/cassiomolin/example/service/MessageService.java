package com.cassiomolin.example.service;

import com.cassiomolin.example.domain.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * Service that provides operations for messages.
 *
 * @author cassiomolin
 */
@ApplicationScoped
public class MessageService {

    @Inject
    private Event<Message> messageEvent;

    public void send(Message message) {
        messageEvent.fire(message);
    }
}
