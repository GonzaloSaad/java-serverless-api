package org.github.serverless.api.arguments.apigw.parsers;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.github.serverless.api.annotations.parameters.PathParameter;
import org.github.serverless.api.arguments.apigw.parsers.types.ParameterTypeAdjuster;
import org.github.serverless.api.exceptions.parsing.path.PathParameterMissingException;

import java.lang.reflect.Parameter;
import java.util.Map;

public class PathParameterParser implements ApiGatewayEventParser {

    private final ParameterTypeAdjuster adjuster;

    public PathParameterParser(ParameterTypeAdjuster adjuster) {
        this.adjuster = adjuster;
    }

    @Override
    public boolean isParameter(Parameter parameter) {
        return parameter.isAnnotationPresent(PathParameter.class);
    }

    @Override
    public Object parse(Parameter parameter, APIGatewayProxyRequestEvent event) {

        Map<String, String> pathParameters = event.getPathParameters();

        PathParameter pathParameter = parameter.getAnnotation(PathParameter.class);
        String pathParameterName = pathParameter.name();

        if (pathParameters != null && !pathParameters.isEmpty() && pathParameters.containsKey(pathParameterName)) {
            String pathParameterValue = pathParameters.get(pathParameterName);
            return adjuster.adjust(parameter, pathParameterValue);
        } else if (pathParameter.required()) {
            throw new PathParameterMissingException(pathParameterName);
        } else {
            return null;
        }
    }
}
