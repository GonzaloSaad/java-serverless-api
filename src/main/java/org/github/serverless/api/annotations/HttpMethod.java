package org.github.serverless.api.annotations;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    ANY("ANY");

    private final String value;

    HttpMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
