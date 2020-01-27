package org.github.serverless.api.arguments.apigw.parsers.types;

import org.github.serverless.api.exceptions.parsing.query.QueryParameterNotValidException;

public class QueryParameterAdjuster extends ParameterTypeAdjuster {
    @Override
    protected RuntimeException failure(Exception e) {
        return new QueryParameterNotValidException(e.getMessage());
    }
}
