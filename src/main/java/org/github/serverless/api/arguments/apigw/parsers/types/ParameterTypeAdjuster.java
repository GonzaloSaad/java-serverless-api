package org.github.serverless.api.arguments.apigw.parsers.types;

import java.lang.reflect.Parameter;

public abstract class ParameterTypeAdjuster {

    public Object adjust(Parameter parameter, String argument) {

        try {
            if (Integer.class.equals(parameter.getType())) {
                return Integer.parseInt(argument);
            }
            return argument;
        } catch (Exception e) {
            throw failure(e);
        }
    }

    protected abstract RuntimeException failure(Exception e);

}
