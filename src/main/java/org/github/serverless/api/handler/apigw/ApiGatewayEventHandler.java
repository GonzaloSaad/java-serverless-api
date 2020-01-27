package org.github.serverless.api.handler.apigw;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import java.io.IOException;
import java.util.Optional;

public class ApiGatewayEventHandler extends EventHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static Logger LOGGER = LogManager.getLogger(ApiGatewayEventHandler.class);


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
    protected APIGatewayProxyResponseEvent digest(APIGatewayProxyRequestEvent input) throws IOException {


        try {
            ApiGatewayEndpointSupplier endpointSupplier = new ApiGatewayEndpointSupplier(input);
            EndpointDispatcher dispatcher = apiRouter.route(endpointSupplier);

            ArgumentExtractor argumentExtractor = new ApiGatewayArgumentExtractor(input);
            return Optional.ofNullable(dispatcher.dispatch(argumentExtractor))
                    .map(outputTransformer::transform)
                    .orElseGet(outputTransformer::transform);

        } catch (APIException e) {
            LOGGER.warn(e);
            return outputTransformer.transform(e);
        } catch (Exception e) {
            LOGGER.error(e);
            return outputTransformer.transform(e);
        }

    }
}
