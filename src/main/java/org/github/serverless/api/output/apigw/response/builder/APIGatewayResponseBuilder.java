package org.github.serverless.api.output.apigw.response.builder;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.Map;

public class APIGatewayResponseBuilder implements ResponseBuilder<APIGatewayProxyResponseEvent> {

    private int statusCode;
    private String body;
    private Map<String, String> headers;

    @Override
    public ResponseBuilder<APIGatewayProxyResponseEvent> withStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    @Override
    public ResponseBuilder<APIGatewayProxyResponseEvent> withBody(String body) {
        this.body = body;
        return this;
    }

    @Override
    public ResponseBuilder<APIGatewayProxyResponseEvent> withHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public APIGatewayProxyResponseEvent build() {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setStatusCode(statusCode);
        responseEvent.setBody(body);
        responseEvent.setHeaders(headers);
        return responseEvent;
    }
}
