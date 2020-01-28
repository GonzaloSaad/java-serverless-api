package org.github.serverless.api;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.github.serverless.api.handler.EventHandler;
import org.github.serverless.api.handler.apigw.ApiGatewayEventHandler;
import org.github.serverless.api.mapper.HandlerMapper;
import org.github.serverless.api.routes.ApiRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ServerlessApiHandler implements RequestStreamHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerlessApiHandler.class);

    private final EventHandler<?, ?> handlerChain;
    private final ApiRouter apiRouter;

    public ServerlessApiHandler() {
        this.beforeInit();
        this.apiRouter = createRouter();
        this.handlerChain = createHandlerChain();
    }

    /**
     * Hook method to add any needed functionality before the
     * controllers initialization
     */
    protected void beforeInit() {

    }

    /**
     * Abstract method exposes the controllers list for registering
     * the API Controllers
     * @return The router for the API
     */
    protected ApiRouter createRouter() {
        List<Object> controllers = new ArrayList<>();
        registerControllers(controllers);
        return new ApiRouter(controllers);
    }

    /**
     * Abstract method exposes the controller list for register
     * the used Controllers
     */
    protected abstract void registerControllers(List<Object> controllers);

    /**
     * This method creates the handler chain that will handle the events.
     * This responds to the Chain of Responsibility Design Pattern
     * @return The first handler of the chain
     */
    private EventHandler<?, ?> createHandlerChain() {
        return new ApiGatewayEventHandler(apiRouter);
    }

    /**
     * Stream handler for AWS Lambda
     */
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

        // Read input stream to string
        LOGGER.debug("Reading InputStream");
        String inputString = readInput(input);
        LOGGER.debug("Input event raw string: {}", inputString);

        // Send the input string message to the handlers chain
        // Pattern used here is Chain Of Responsibility
        LOGGER.debug("Handling event");
        Object response = handlerChain.handle(inputString);
        LOGGER.debug("Object returned after handling: {}", response);

        // Convert output object to json and then to bytes
        byte[] outputBytes = getOutputBytes(response);

        // Output bytes to output stream
        output.write(outputBytes);
    }

    /**
     * This method parses the InputStream of AWS Lambda to
     * a raw string representation of the event
     * @return The event in a string representation
     */
    private String readInput(InputStream input) {
        return new BufferedReader(new InputStreamReader(input))
                .lines()
                .parallel()
                .collect(Collectors.joining());
    }

    /**
     * This method converts an object to JSON bytes
     * @return The bytes to return to the AWS Lambda Invoker
     */
    private byte[] getOutputBytes(Object response) throws JsonProcessingException {
        return HandlerMapper.getMapper()
                .writeValueAsBytes(response);
    }
}
