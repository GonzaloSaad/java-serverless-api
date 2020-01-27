package org.github.serverless.api.arguments.apigw.parsers.types;

import org.github.serverless.api.exceptions.parsing.path.PathParameterNotValidException;

public class PathParameterAdjuster extends ParameterTypeAdjuster {
    @Override
    protected RuntimeException failure(Exception e) {
        return new PathParameterNotValidException(e.getMessage());
    }
}
