package com.cassiomolin.example.api.websockets;

import com.cassiomolin.example.domain.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Encoder for {@link Message}.
 *
 * @author cassiomolin
 */
class MessageEncoder implements Encoder.Text<Message> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init(EndpointConfig ec) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public String encode(Message message) throws EncodeException {
        try {
            return mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new EncodeException(message, e.getMessage(), e);
        }
    }
}
