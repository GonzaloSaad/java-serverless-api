package org.github.serverless.api.output.apigw.response.headers;

import java.util.HashMap;
import java.util.Map;

public class CORSResponseHeader implements ResponseHeader {
    @Override
    public Map<String, String> headers() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Access-Control-Allow-Origin", "*");
        return headers;
    }
}
