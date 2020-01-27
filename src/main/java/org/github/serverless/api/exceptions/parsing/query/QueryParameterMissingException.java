package org.github.serverless.api.exceptions.parsing.query;

import org.github.serverless.api.exceptions.apigw.APIException;

import java.net.HttpURLConnection;

public class QueryParameterMissingException extends APIException {
    private static final String ERROR_MESSAGE_TEMPLATE = "Query parameter [%s] missing";

    public QueryParameterMissingException(String pathVariable) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, pathVariable), HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
