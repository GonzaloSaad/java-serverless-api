package org.github.serverless.api.handler;

import org.github.serverless.api.handler.exceptions.EventHandlerNotFoundException;

import java.io.IOException;
import java.util.Optional;

public abstract class EventHandler<I, O> {

    private EventHandler<?, ?> nextEventHandler;

    public Object handle(String inputString) throws IOException {

        if (isHandlerFor(inputString)) {
            I input = transform(inputString);
            return digest(input);
        }
        return Optional.ofNullable(nextEventHandler)
                .orElseThrow(EventHandlerNotFoundException::new)
                .handle(inputString);
    }


    protected abstract boolean isHandlerFor(String eventString);

    protected abstract I transform(String eventString) throws IOException;

    protected abstract O digest(I input) throws IOException;

    public void setNextEventHandler(EventHandler<?, ?> nextEventHandler) {
        this.nextEventHandler = nextEventHandler;
    }
}
