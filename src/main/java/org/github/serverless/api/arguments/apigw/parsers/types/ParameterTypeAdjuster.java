package org.github.serverless.api.arguments.apigw.parsers.types;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public abstract class ParameterTypeAdjuster {

    public Object adjust(Parameter parameter, String argument) {

        try {
            Type type = parameter.getType();
            if (Integer.class.equals(type)) {
                return Integer.parseInt(argument);
            } else if (Boolean.class.equals(type)) {
                return Boolean.parseBoolean(argument);
            } else if (List.class.equals(type)){
                return Arrays.asList(argument.split(","));
            } else if (Date.class.equals(type)) {
                return new SimpleDateFormat("dd-MMM-yyyy",  Locale.ENGLISH).parse(argument);
            }
            return argument;
        } catch (Exception e) {
            throw failure(e);
        }
    }

    protected abstract RuntimeException failure(Exception e);

}
