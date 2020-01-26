package org.github.serverless.api.exceptions;

public class ExceptionMessageContainer {

    private final String message;

    public ExceptionMessageContainer(Throwable throwable) {
        message = throwable.getMessage();
    }

    public String getMessage() {
        return message;
    }
}
