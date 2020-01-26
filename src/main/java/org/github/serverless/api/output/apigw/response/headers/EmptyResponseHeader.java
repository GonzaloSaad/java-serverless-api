package org.github.serverless.api.output.apigw.response.headers;

import java.util.HashMap;
import java.util.Map;

public class EmptyResponseHeader implements ResponseHeader {
    @Override
    public Map<String, String> headers() {
        return new HashMap<>();
    }
}
