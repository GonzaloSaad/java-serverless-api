package org.github.serverless.api.exceptions.apigw;


public class APIException extends RuntimeException {
    private final int statusCode;

    public APIException(String s, int status) {
        super(s);
        this.statusCode = status;
    }

    public APIException(String s, int status, Throwable throwable) {
        super(s, throwable);
        this.statusCode = status;
    }

    public APIException(Throwable throwable, int status) {
        super(throwable);
        this.statusCode = status;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
