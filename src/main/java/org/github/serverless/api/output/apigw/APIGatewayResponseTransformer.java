package org.github.serverless.api.output.apigw;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.Map;

public interface APIGatewayResponseTransformer {
    APIGatewayProxyResponseEvent transform(Map<String, String> headers);
}
