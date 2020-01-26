package org.github.serverless.api;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.github.serverless.api.handler.EventHandler;
import org.github.serverless.api.handler.apigw.ApiGatewayEventHandler;
import org.github.serverless.api.mapper.HandlerMapper;
import org.github.serverless.api.routes.ApiRouter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ServerlessApiHandler implements RequestStreamHandler {

    private final EventHandler<?, ?> handlerChain;
    private final ApiRouter apiRouter;

    public ServerlessApiHandler() {
        this.beforeInit();
        this.apiRouter = createRouter();
        this.handlerChain = createHandlerChain();
    }


    protected void beforeInit() {

    }

    protected ApiRouter createRouter() {
        List<Object> controllers = new ArrayList<>();
        registerControllers(controllers);
        return new ApiRouter(controllers);
    }

    protected abstract void registerControllers(List<Object> handlers);

    private EventHandler<?, ?> createHandlerChain() {
        return new ApiGatewayEventHandler(apiRouter);
    }

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        String inputString = readInput(input);
        Object response = handlerChain.handle(inputString);
        byte[] outputBytes = getOutputBytes(response);
        output.write(outputBytes);
    }

    private String readInput(InputStream input) {
        return new BufferedReader(new InputStreamReader(input))
                .lines()
                .parallel()
                .collect(Collectors.joining());
    }

    private byte[] getOutputBytes(Object response) throws JsonProcessingException {
        return HandlerMapper.getMapper()
                .writeValueAsBytes(response);
    }
}
