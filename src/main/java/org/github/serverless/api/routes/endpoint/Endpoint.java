package org.github.serverless.api.routes.endpoint;

import java.util.Objects;

public class Endpoint {
    private final String resource;
    private final String httpMethod;

    public Endpoint(String resource, String httpMethod) {
        this.resource = resource;
        this.httpMethod = httpMethod;
    }

    public String getResource() {
        return resource;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endpoint endpoint = (Endpoint) o;
        return Objects.equals(resource, endpoint.resource) &&
                Objects.equals(httpMethod, endpoint.httpMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resource, httpMethod);
    }
}
