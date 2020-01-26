package org.github.serverless.api.exceptions.parsing;

import org.github.serverless.api.exceptions.apigw.APIException;

import java.net.HttpURLConnection;

public class BodyNotValidException extends APIException {
    public BodyNotValidException(String message) {
        super(message, HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
