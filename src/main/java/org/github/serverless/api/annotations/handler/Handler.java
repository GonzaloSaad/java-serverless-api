package org.github.serverless.api.annotations.handler;

import org.github.serverless.api.annotations.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Handler {
    String resource() default "";
    HttpMethod method() default HttpMethod.GET;
}
