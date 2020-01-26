package org.github.serverless.api.arguments;

import java.lang.reflect.Method;

public interface ArgumentExtractor {
    Object[] extract(Method method);
}
