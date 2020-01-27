package org.github.serverless.api.arguments.apigw.parsers;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.github.serverless.api.annotations.parameters.QueryParameter;
import org.github.serverless.api.arguments.apigw.parsers.types.ParameterTypeAdjuster;
import org.github.serverless.api.exceptions.parsing.query.QueryParameterMissingException;

import java.lang.reflect.Parameter;
import java.util.Map;

public class QueryParameterParser implements ApiGatewayEventParser {

    private final ParameterTypeAdjuster adjuster;

    public QueryParameterParser(ParameterTypeAdjuster adjuster) {
        this.adjuster = adjuster;
    }

    @Override
    public boolean isParameter(Parameter parameter) {
        return parameter.isAnnotationPresent(QueryParameter.class);
    }

    @Override
    public Object parse(Parameter parameter, APIGatewayProxyRequestEvent event) {
        Map<String, String> queryParameters = event.getQueryStringParameters();

        QueryParameter queryParameter = parameter.getAnnotation(QueryParameter.class);
        String queryParameterName = queryParameter.name();

        if (queryParameters != null && !queryParameters.isEmpty() && queryParameters.containsKey(queryParameterName)) {
            String queryParameterValue = queryParameters.get(queryParameterName);
            return adjuster.adjust(parameter, queryParameterValue);
        } else if (queryParameter.required()) {
            throw new QueryParameterMissingException(queryParameterName);
        } else {
            return null;
        }
    }
}
