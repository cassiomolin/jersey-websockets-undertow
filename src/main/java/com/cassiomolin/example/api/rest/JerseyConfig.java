package com.cassiomolin.example.api.rest;

import com.cassiomolin.example.api.rest.resources.MessageResource;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Jersey configuration.
 *
 * @author cassiomolin
 */
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(MessageResource.class);
        register(JacksonJaxbJsonProvider.class);
    }
}
