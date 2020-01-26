package org.github.serverless.api.routes;

import org.github.serverless.api.annotations.handler.Handler;
import org.github.serverless.api.routes.endpoint.supplier.EndpointSupplier;
import org.github.serverless.api.routes.exceptions.RouteNotFoundException;
import org.github.serverless.api.routes.dispatcher.EndpointDispatcher;
import org.github.serverless.api.routes.endpoint.Endpoint;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiRouter {

    private List<Object> controllers;
    private Map<Endpoint, EndpointDispatcher> routes;

    public ApiRouter(List<Object> controllers) {
        this.controllers = controllers;
        this.routes = new HashMap<>();
    }

    public EndpointDispatcher route(EndpointSupplier endpointSupplier) {
        Endpoint endpoint = endpointSupplier.get();
        routes.putIfAbsent(endpoint, seekForController(endpoint));
        return routes.get(endpoint);
    }

    private EndpointDispatcher seekForController(Endpoint endpoint) {
        String resource = endpoint.getResource();
        String httpMethod = endpoint.getHttpMethod();

        for (Object controller : controllers) {

            for (Method method : controller.getClass().getMethods()) {

                if (method.isAnnotationPresent(Handler.class)) {
                    Handler handlerAnnotation = method.getAnnotation(Handler.class);

                    if (handlerAnnotation.method().getValue().equals(httpMethod) &&
                            handlerAnnotation.resource().equals(resource)) {

                        return new EndpointDispatcher(controller, method);
                    }
                }
            }
        }
        throw new RouteNotFoundException();
    }
}
