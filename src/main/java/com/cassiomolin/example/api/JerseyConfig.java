package com.cassiomolin.example.api;

import com.cassiomolin.example.api.resources.MessageResource;
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
