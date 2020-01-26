package org.github.serverless.api.output;


import org.github.serverless.api.exceptions.apigw.APIException;

public interface OutputTransformer<O> {
    O transform();

    O transform(Object object);

    O transform(APIException exception);

    O transform(Throwable throwable);
}
