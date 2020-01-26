package org.github.serverless.api.arguments.apigw.parsers;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.github.serverless.api.annotations.parameters.BodyParameter;
import org.github.serverless.api.exceptions.parsing.BodyNotValidException;
import org.github.serverless.api.handler.apigw.mapper.ApiGatewayMapper;

import java.io.IOException;
import java.lang.reflect.Parameter;

public class BodyParameterParser implements ApiGatewayEventParser{

    @Override
    public boolean isParameter(Parameter parameter) {
        return parameter.isAnnotationPresent(BodyParameter.class);
    }

    @Override
    public Object parse(Parameter parameter, APIGatewayProxyRequestEvent event) {
        String bodyString = event.getBody();

        if (bodyString != null && !bodyString.isEmpty()) {

            Class<?> parameterType = parameter.getType();

            try {
                return ApiGatewayMapper.getMapper()
                        .readValue(bodyString, parameterType);
            } catch (IOException e) {
                throw new BodyNotValidException(e.getMessage());
            }
        }
        return null;
    }
}
