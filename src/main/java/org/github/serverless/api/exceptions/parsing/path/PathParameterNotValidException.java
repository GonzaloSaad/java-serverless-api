package org.github.serverless.api.exceptions.parsing.path;

import org.github.serverless.api.exceptions.apigw.APIException;

import java.net.HttpURLConnection;

public class PathParameterNotValidException extends APIException {
    public PathParameterNotValidException(String message) {
        super(message, HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
