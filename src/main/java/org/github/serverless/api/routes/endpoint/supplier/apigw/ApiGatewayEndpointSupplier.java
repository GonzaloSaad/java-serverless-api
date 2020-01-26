package org.github.serverless.api.routes.endpoint.supplier.apigw;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.github.serverless.api.routes.endpoint.Endpoint;
import org.github.serverless.api.routes.endpoint.supplier.EndpointSupplier;

public class ApiGatewayEndpointSupplier implements EndpointSupplier {

    private final APIGatewayProxyRequestEvent event;

    public ApiGatewayEndpointSupplier(APIGatewayProxyRequestEvent event) {
        this.event = event;
    }

    @Override
    public Endpoint get() {
        String resource = event.getResource();
        String httpMethod = event.getHttpMethod();
        return new Endpoint(resource, httpMethod);
    }
}
