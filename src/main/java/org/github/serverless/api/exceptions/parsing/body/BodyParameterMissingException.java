package org.github.serverless.api.exceptions.parsing.body;

import org.github.serverless.api.exceptions.apigw.APIException;

import java.net.HttpURLConnection;

public class BodyParameterMissingException extends APIException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Body parameter [%s] missing";

    public BodyParameterMissingException(String pathVariable) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, pathVariable), HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
