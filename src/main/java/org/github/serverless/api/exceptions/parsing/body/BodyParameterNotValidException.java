package org.github.serverless.api.exceptions.parsing.body;

import org.github.serverless.api.exceptions.apigw.APIException;

import java.net.HttpURLConnection;

public class BodyParameterNotValidException extends APIException {
    public BodyParameterNotValidException(String message) {
        super(message, HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
