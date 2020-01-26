package org.github.serverless.api.output.apigw;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.github.serverless.api.exceptions.apigw.APIException;
import org.github.serverless.api.output.OutputTransformer;
import org.github.serverless.api.output.apigw.response.builder.APIGatewayResponseBuilder;
import org.github.serverless.api.output.apigw.response.headers.EmptyResponseHeader;
import org.github.serverless.api.output.apigw.response.headers.ResponseHeader;
import org.github.serverless.api.serializer.DefaultJSONSerializer;

import java.net.HttpURLConnection;

public class APIGatewayOutputTransformer implements OutputTransformer<APIGatewayProxyResponseEvent> {

    private ResponseHeader header;

    public APIGatewayOutputTransformer() {
        this.header = new EmptyResponseHeader();
    }

    public APIGatewayOutputTransformer(ResponseHeader header) {
        this.header = header;
    }

    public APIGatewayProxyResponseEvent transform() {
        return new APIGatewayResponseBuilder()
                .withStatusCode(HttpURLConnection.HTTP_NO_CONTENT)
                .withHeaders(header.headers())
                .build();
    }

    public APIGatewayProxyResponseEvent transform(Object object) {
        return new APIGatewayResponseBuilder()
                .withStatusCode(HttpURLConnection.HTTP_OK)
                .withBody(getBody(object))
                .withHeaders(header.headers())
                .build();
    }

    public APIGatewayProxyResponseEvent transform(APIException exception) {
        return new APIGatewayResponseBuilder()
                .withStatusCode(exception.getStatusCode())
                .withBody(getBody(exception))
                .withHeaders(header.headers())
                .build();
    }


    public APIGatewayProxyResponseEvent transform(Throwable throwable) {
        return new APIGatewayResponseBuilder()
                .withStatusCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
                .withBody(getBody(throwable))
                .withHeaders(header.headers())
                .build();
    }

    private String getBody(Object object) {
        DefaultJSONSerializer serializer = new DefaultJSONSerializer(object);
        return serializer.serialize();
    }

    private String getBody(Throwable throwable) {
        DefaultJSONSerializer serializer = new DefaultJSONSerializer(throwable);
        return serializer.serialize();
    }
}
