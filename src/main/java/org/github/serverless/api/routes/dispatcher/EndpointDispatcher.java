package org.github.serverless.api.routes.dispatcher;

import org.github.serverless.api.arguments.ArgumentExtractor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EndpointDispatcher {

    private final Object controller;
    private final Method method;

    public EndpointDispatcher(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    public Object dispatch(ArgumentExtractor argumentExtractor) throws InvocationTargetException, IllegalAccessException {
        Object[] arguments = argumentExtractor.extract(method);
        return method.invoke(controller, arguments);
    }
}
