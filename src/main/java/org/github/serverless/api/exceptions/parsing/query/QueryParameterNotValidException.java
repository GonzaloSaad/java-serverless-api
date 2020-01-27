package org.github.serverless.api.exceptions.parsing.query;

import org.github.serverless.api.exceptions.apigw.APIException;

import java.net.HttpURLConnection;

public class QueryParameterNotValidException extends APIException {
    public QueryParameterNotValidException(String message) {
        super(message, HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
