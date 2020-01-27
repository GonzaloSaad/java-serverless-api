package org.github.serverless.api.exceptions.parsing.path;

import org.github.serverless.api.exceptions.apigw.APIException;

import java.net.HttpURLConnection;

public class PathParameterMissingException extends APIException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Path parameter [%s] missing";

    public PathParameterMissingException(String pathVariable) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, pathVariable), HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
