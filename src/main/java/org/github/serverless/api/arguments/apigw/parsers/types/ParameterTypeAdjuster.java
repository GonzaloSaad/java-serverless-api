package org.github.serverless.api.arguments.apigw.parsers.types;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public abstract class ParameterTypeAdjuster {

    public Object adjust(Parameter parameter, String argument) {

        try {
            if (Integer.class.equals(parameter.getType())) {
                return Integer.parseInt(argument);
            } else if (Boolean.class.equals(parameter.getType())) {
                return Boolean.parseBoolean(argument);
            } else if (List.class.equals(parameter.getType())){
                return Arrays.asList(argument.split(","));
            }
            return argument;
        } catch (Exception e) {
            throw failure(e);
        }
    }

    protected abstract RuntimeException failure(Exception e);

}
