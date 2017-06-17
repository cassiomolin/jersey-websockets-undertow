package com.cassiomolin.example.api.endpoints;

import javax.enterprise.inject.spi.CDI;
import javax.websocket.server.ServerEndpointConfig;

/**
 * Endpoint configurator. The endpoints instances are created and managed by CDI.
 *
 * @author cassiomolin
 */
class CdiAwareConfigurator extends ServerEndpointConfig.Configurator {

    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return CDI.current().select(endpointClass).get();
    }
}