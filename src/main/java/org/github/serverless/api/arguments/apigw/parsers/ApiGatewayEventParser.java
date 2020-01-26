package org.github.serverless.api.arguments.apigw.parsers;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;

import java.lang.reflect.Parameter;

public interface ApiGatewayEventParser {
    boolean isParameter(Parameter parameter);
    Object parse(Parameter parameter, APIGatewayProxyRequestEvent event);
}
