package com.cassiomolin.example.domain;

/**
 * Domain model that represents a message.
 *
 * @author cassiomolin
 */
public class Message {

    private String message;

    public String getMessage() {
        return message;
    }

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }
}
