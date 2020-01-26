package org.github.serverless.api.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.github.serverless.api.exceptions.ExceptionMessageContainer;
import org.github.serverless.api.mapper.HandlerMapper;

public class DefaultJSONSerializer implements JSONSerializer {

    private static Logger LOGGER = LogManager.getLogger(DefaultJSONSerializer.class);

    private final Object object;

    public DefaultJSONSerializer(Object object) {
        this.object = object;
    }

    public DefaultJSONSerializer(Throwable throwable) {
        this.object = new ExceptionMessageContainer(throwable);
    }

    @Override
    public String serialize() {
        try {
            LOGGER.debug("Serializing return object.");
            return HandlerMapper.getMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Cannot serialize return object.", e);
        }
    }
}
