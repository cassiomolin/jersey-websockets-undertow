package com.cassiomolin.example.api.rest.resources;

import com.cassiomolin.example.domain.Message;
import com.cassiomolin.example.service.MessageService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Component that exposes a REST API for sending {@link Message}s.
 *
 * @author cassiomolin
 */
@RequestScoped
@Path("/messages")
public class MessageResource {

    @Inject
    private MessageService messageService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response send(Message message) {
        messageService.send(message);
        return Response.ok().build();
    }
}
