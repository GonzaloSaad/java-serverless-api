package org.github.serverless.api.output.apigw.response.builder;


import java.util.Map;

public interface ResponseBuilder<T> {
    ResponseBuilder<T> withStatusCode(int statusCode);
    ResponseBuilder<T> withBody(String body);
    ResponseBuilder<T> withHeaders(Map<String, String> headers);
    T build();
}
