package org.github.serverless.api.arguments.apigw.parsers;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.github.serverless.api.annotations.parameters.QueryParameter;

import java.lang.reflect.Parameter;
import java.util.Map;

public class QueryParameterParser implements ApiGatewayEventParser {
    @Override
    public boolean isParameter(Parameter parameter) {
        return parameter.isAnnotationPresent(QueryParameter.class);
    }

    @Override
    public Object parse(Parameter parameter, APIGatewayProxyRequestEvent event) {
        Map<String, String> queryParameters = event.getQueryStringParameters();

        if (queryParameters != null && !queryParameters.isEmpty()) {
            QueryParameter queryParameter = parameter.getAnnotation(QueryParameter.class);
            String queryParameterName = queryParameter.name();
            String argument = queryParameters.get(queryParameterName);

            return adjustReturnType(parameter, argument);
        }
        return null;
    }

    private Object adjustReturnType(Parameter parameter, String argument) {
        if (Integer.class.equals(parameter.getType())) {
            return Integer.parseInt(argument);
        }
        return argument;
    }

}
