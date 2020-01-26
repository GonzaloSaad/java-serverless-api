package org.github.serverless.api.arguments.apigw;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.github.serverless.api.arguments.ArgumentExtractor;
import org.github.serverless.api.arguments.apigw.parsers.ApiGatewayEventParser;
import org.github.serverless.api.arguments.apigw.parsers.BodyParameterParser;
import org.github.serverless.api.arguments.apigw.parsers.PathParameterParser;
import org.github.serverless.api.arguments.apigw.parsers.QueryParameterParser;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ApiGatewayArgumentExtractor implements ArgumentExtractor {

    private final APIGatewayProxyRequestEvent event;
    private final List<ApiGatewayEventParser> eventParsers;

    public ApiGatewayArgumentExtractor(APIGatewayProxyRequestEvent event) {
        this.event = event;
        this.eventParsers = Arrays.asList(
                new PathParameterParser(),
                new QueryParameterParser(),
                new BodyParameterParser()
        );

    }

    @Override
    public Object[] extract(Method method) {
        return Stream.of(method.getParameters())
                .parallel()
                .map(parameter ->
                        eventParsers.parallelStream()
                                .filter(ep -> ep.isParameter(parameter))
                                .findFirst()
                                .map(ep -> ep.parse(parameter, event))
                                .orElse(null))
                .toArray();
    }
}
