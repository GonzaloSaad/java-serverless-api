package org.github.serverless.api.handler.apigw;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.github.serverless.api.arguments.ArgumentExtractor;
import org.github.serverless.api.arguments.apigw.ApiGatewayArgumentExtractor;
import org.github.serverless.api.exceptions.apigw.APIException;
import org.github.serverless.api.handler.EventHandler;
import org.github.serverless.api.handler.apigw.mapper.ApiGatewayMapper;
import org.github.serverless.api.output.OutputTransformer;
import org.github.serverless.api.output.apigw.APIGatewayOutputTransformer;
import org.github.serverless.api.routes.ApiRouter;
import org.github.serverless.api.routes.dispatcher.EndpointDispatcher;
import org.github.serverless.api.routes.endpoint.supplier.apigw.ApiGatewayEndpointSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class ApiGatewayEventHandler extends EventHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final static Logger LOGGER = LoggerFactory.getLogger(ApiGatewayEventHandler.class);


    private final ApiRouter apiRouter;
    private final OutputTransformer<APIGatewayProxyResponseEvent> outputTransformer;

    public ApiGatewayEventHandler(ApiRouter apiRouter) {
        this.apiRouter = apiRouter;
        this.outputTransformer = new APIGatewayOutputTransformer();

    }

    @Override
    public boolean isHandlerFor(String input) {
        return input.contains("httpMethod");
    }

    @Override
    protected APIGatewayProxyRequestEvent transform(String eventString) throws IOException {
        return ApiGatewayMapper
                .getMapper()
                .readValue(eventString, APIGatewayProxyRequestEvent.class);
    }

    @Override
    protected APIGatewayProxyResponseEvent digest(APIGatewayProxyRequestEvent input) {

        LOGGER.debug("Received event on {} with method {}", input.getResource(), input.getHttpMethod());

        try {

            // Create Endpoint supplier for the APIGateway event
            LOGGER.debug("Creating Endpoint object for ApiRouter");
            ApiGatewayEndpointSupplier endpointSupplier = new ApiGatewayEndpointSupplier(input);

            // Look for dispatcher in ApiRouter
            LOGGER.debug("Looking for dispatcher in ApiRouter");
            EndpointDispatcher dispatcher = apiRouter.route(endpointSupplier);

            // Create argument extractor for APIGateway event
            LOGGER.debug("Creating argument extractor");
            ArgumentExtractor argumentExtractor = new ApiGatewayArgumentExtractor(input);

            LOGGER.debug("Dispatch event to handler method");
            return Optional.ofNullable(dispatcher.dispatch(argumentExtractor))
                    .map(outputTransformer::transform)
                    .orElseGet(outputTransformer::transform);

        } catch (APIException e) {
            LOGGER.warn("APIException occurred", e);
            return outputTransformer.transform(e);
        } catch (Throwable e) {
            LOGGER.error("Exception occurred", e);
            return outputTransformer.transform(e);
        }

    }
}
