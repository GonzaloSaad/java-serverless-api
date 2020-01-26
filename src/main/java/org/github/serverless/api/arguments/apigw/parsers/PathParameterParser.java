package org.github.serverless.api.arguments.apigw.parsers;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.github.serverless.api.annotations.parameters.PathParameter;

import java.lang.reflect.Parameter;
import java.util.Map;

public class PathParameterParser implements ApiGatewayEventParser {

    @Override
    public boolean isParameter(Parameter parameter) {
        return parameter.isAnnotationPresent(PathParameter.class);
    }

    @Override
    public Object parse(Parameter parameter, APIGatewayProxyRequestEvent event) {

        Map<String, String> pathParameters = event.getPathParameters();

        if (pathParameters != null && !pathParameters.isEmpty()) {
            PathParameter pathParameter = parameter.getAnnotation(PathParameter.class);
            String pathParameterName = pathParameter.name();
            String argument = pathParameters.get(pathParameterName);

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
