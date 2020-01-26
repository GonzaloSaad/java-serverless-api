package org.github.serverless.api.routes.exceptions;

import org.github.serverless.api.exceptions.apigw.APIException;

import java.net.HttpURLConnection;

public class RouteNotFoundException extends APIException {

    private static final String ERROR_MESSAGE = "Route not found for request";

    public RouteNotFoundException() {
        super(ERROR_MESSAGE, HttpURLConnection.HTTP_NOT_FOUND);
    }
}
